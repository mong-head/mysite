package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.BoardVo;

public class BoardRepository {
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			// 1. JDBC driver loading
			// loading 후 Driver Class가 DriverManager만듦 - 우리가 new로 drivermanager만들 필요없음
			Class.forName("org.mariadb.jdbc.Driver");

			// 2. DriverManager - get connection
			String url = "jdbc:mysql://192.168.254.40:3307/webdb?characterEncoding=utf8"; // linux server ip
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			conn.createStatement();
			System.out.println("success connection");
		} catch (ClassNotFoundException e) {
			System.out.println("fail to load driver:" + e);
		}
		return conn;

	}

	public boolean Insert(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			conn = new ForConnection().getConnection();

			// 3. prepare Statement
			String sql = "insert into board values(null,?,?, now(), ?, ?,?,?,?)";
			pstmt = conn.prepareStatement(sql);

			// 4. SQL binging
			pstmt.setString(1, vo.getTitle()); // parameter mapping
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, vo.getHit());
			pstmt.setInt(4, vo.getGroupNo());
			pstmt.setInt(5, vo.getOrderNo());
			pstmt.setInt(6, vo.getDepth());
			pstmt.setLong(7, vo.getUserNo());

			// 5. SQL execute
			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (SQLException e) {
			// 2. 관련 : linux 꺼져있을 때 등등 connection안될 때
			System.out.println("error :" + e);
		} finally {
			// clean-up; 자원정리는 만들어진 순서 거꾸로 하기
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
				System.out.println("connection close error:" + e);
			}
		}
		return result;
	}

	public List<BoardVo> findAll() {
		List<BoardVo> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1,2 : driver loading, connection
			conn = new ForConnection().getConnection();

			// 3. prepare sql statement
			String sql = "select b.no,title,contents,reg_date,hit,group_no,order_no,depth,b.user_no, u.name"
					+ " from board b" + " join user u on u.no = b.user_no " + " order by group_no desc , order_no asc";
			pstmt = conn.prepareStatement(sql);

			// 4. SQL실행 ( no binding )
			rs = pstmt.executeQuery();

			// 6. result 가져오기
			while (rs.next() /* 각각의 행 가지고 오기 */) {
				long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				String reg_date = rs.getString(4);
				int hit = rs.getInt(5);
				int group_no = rs.getInt(6);
				int order_no = rs.getInt(7);
				int depth = rs.getInt(8);
				int user_no = rs.getInt(9);
				String user_name = rs.getString(10);

				// mapping
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setRegDate(reg_date);
				vo.setHit(hit);
				vo.setGroupNo(group_no);
				vo.setOrderNo(order_no);
				vo.setDepth(depth);
				vo.setUserNo(user_no);
				vo.setUserName(user_name);

				result.add(vo);
			}

		} catch (SQLException e) {
			// 2. 관련 : linux 꺼져있을 때 등등 connection안될 때
			System.out.println("error :" + e);
		} finally {
			// clean-up; 자원정리는 만들어진 순서 거꾸로 하기
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
				System.out.println("connection close error:" + e);
			}
		}
		return result;
	}

	public boolean delete(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			// 1,2 : driver loading, connection
			conn = new ForConnection().getConnection();

			// 3. Statement 생성 - query 실행위함
			String sql = "delete from board where no = ?";
			pstmt = conn.prepareStatement(sql);

			// 4. binding
			pstmt.setLong(1, vo.getNo());

			// 5. SQL실행
			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (SQLException e) {
			// 2. 관련 : linux 꺼져있을 때 등등 connection안될 때
			System.out.println("error :" + e);
		} finally {
			// clean-up; 자원정리는 만들어진 순서 거꾸로 하기
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
				System.out.println("connection close error:" + e);
			}
		}
		return result;
	}

	public int findMaxGroupNo() {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1,2 : driver loading, connection
			conn = new ForConnection().getConnection();

			// 3. prepare sql statement
			String sql = "select max(group_no) from board";
			pstmt = conn.prepareStatement(sql);

			// 4. SQL실행 ( no binding )
			rs = pstmt.executeQuery();

			// 6. result 가져오기
			if (rs.next()) {

				result = rs.getInt(1);

			}

		} catch (SQLException e) {
			// 2. 관련 : linux 꺼져있을 때 등등 connection안될 때
			System.out.println("error :" + e);
		} finally {
			// clean-up; 자원정리는 만들어진 순서 거꾸로 하기
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
				System.out.println("connection close error:" + e);
			}
		}
		return result;
	}

	public BoardVo findByNo(long no) {
		BoardVo result = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1,2 : driver loading, connection
			conn = new ForConnection().getConnection();

			// 3. prepare sql statement
			String sql = "select title,contents,hit,group_no,order_no,depth,user_no,reg_date from board where no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);

			// 4. SQL실행 ( no binding )
			rs = pstmt.executeQuery();

			// 6. result 가져오기
			if (rs.next()) {

				String title = rs.getString(1);
				String contents = rs.getString(2);
				int hit = rs.getInt(3);
				int groupNo = rs.getInt(4);
				int orderNo = rs.getInt(5);
				int depth = rs.getInt(6);
				long userNo = rs.getLong(7);
				String regDate = rs.getString(8);

				result = new BoardVo();
				result.setNo(no);
				result.setTitle(title);
				result.setContents(contents);
				result.setHit(hit);
				result.setGroupNo(groupNo);
				result.setOrderNo(orderNo);
				result.setDepth(depth);
				result.setUserNo(userNo);
				result.setRegDate(regDate);
			}

		} catch (SQLException e) {
			// 2. 관련 : linux 꺼져있을 때 등등 connection안될 때
			System.out.println("error :" + e);
		} finally {
			// clean-up; 자원정리는 만들어진 순서 거꾸로 하기
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
				System.out.println("connection close error:" + e);
			}
		}
		return result;
	}

	public boolean updateHit(BoardVo vo) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			// 1,2 : driver loading, connection
			conn = new ForConnection().getConnection();

			// 3. Statement 생성 - query 실행위함
			String sql = "update board set hit = ? where no = ?";
			pstmt = conn.prepareStatement(sql);

			// 4. binding
			pstmt.setInt(1, vo.getHit());
			pstmt.setLong(2, vo.getNo());

			// 5. SQL실행
			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (SQLException e) {
			// 2. 관련 : linux 꺼져있을 때 등등 connection안될 때
			System.out.println("error :" + e);
		} finally {
			// clean-up; 자원정리는 만들어진 순서 거꾸로 하기
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
				System.out.println("connection close error:" + e);
			}
		}
		return result;

	}

	public boolean updateOrderNo(BoardVo vo) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			// 1,2 : driver loading, connection
			conn = new ForConnection().getConnection();

			// 3. Statement 생성 - query 실행위함
			String sql = "update board set order_no = ? where no = ?";
			pstmt = conn.prepareStatement(sql);

			// 4. binding
			pstmt.setInt(1, vo.getOrderNo() + 1);
			pstmt.setLong(2, vo.getNo());

			// 5. SQL실행
			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (SQLException e) {
			// 2. 관련 : linux 꺼져있을 때 등등 connection안될 때
			System.out.println("error :" + e);
		} finally {
			// clean-up; 자원정리는 만들어진 순서 거꾸로 하기
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
				System.out.println("connection close error:" + e);
			}
		}
		return result;

	}

	public boolean update(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			// 1,2 : driver loading, connection
			conn = new ForConnection().getConnection();

			// 3. Statement 생성 - query 실행위함
			String sql = "update board set title = ? , contents = ? where no = ?";
			pstmt = conn.prepareStatement(sql);

			// 4. binding
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getNo());

			// 5. SQL실행
			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (SQLException e) {
			// 2. 관련 : linux 꺼져있을 때 등등 connection안될 때
			System.out.println("error :" + e);
		} finally {
			// clean-up; 자원정리는 만들어진 순서 거꾸로 하기
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
				System.out.println("connection close error:" + e);
			}
		}
		return result;

	}

	public List<BoardVo> findByTitle(String kwd) {
		List<BoardVo> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1,2 : driver loading, connection
			conn = new ForConnection().getConnection();

			// 3. prepare sql statement + binding
			String sql = "select b.no,title,contents,reg_date,hit,group_no,order_no,depth, u.no, u.name"
					+ " from board b"
					+ " join user u on u.no = b.user_no"
					+ " where title like ? "
					+ " order by group_no desc , order_no asc";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+kwd+"%");

			// 4. execute
			rs = pstmt.executeQuery();

			// 6. result 가져오기
			while (rs.next() /* 각각의 행 가지고 오기 */) {
				long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				String reg_date = rs.getString(4);
				int hit = rs.getInt(5);
				int group_no = rs.getInt(6);
				int order_no = rs.getInt(7);
				int depth = rs.getInt(8);
				int user_no = rs.getInt(9);
				String user_name = rs.getString(10);

				// mapping
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setRegDate(reg_date);
				vo.setHit(hit);
				vo.setGroupNo(group_no);
				vo.setOrderNo(order_no);
				vo.setDepth(depth);
				vo.setUserNo(user_no);
				vo.setUserName(user_name);

				result.add(vo);
			}

		} catch (SQLException e) {
			// 2. 관련 : linux 꺼져있을 때 등등 connection안될 때
			System.out.println("error :" + e);
		} finally {
			// clean-up; 자원정리는 만들어진 순서 거꾸로 하기
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
				System.out.println("connection close error:" + e);
			}
		}
		return result;
	}
	public List<BoardVo> findByTitleContents(String kwd) {
		List<BoardVo> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1,2 : driver loading, connection
			conn = new ForConnection().getConnection();

			// 3. prepare sql statement + binding
			String sql = "select b.no,title,contents,reg_date,hit,group_no,order_no,depth, u.no, u.name"
					+ " from board b"
					+ " join user u on u.no = b.user_no"
					+ " where title like ? or contents like ? "
					+ " order by group_no desc , order_no asc";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+kwd+"%");
			pstmt.setString(2, "%"+kwd+"%");

			// 4. execute
			rs = pstmt.executeQuery();

			// 6. result 가져오기
			while (rs.next() /* 각각의 행 가지고 오기 */) {
				long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				String reg_date = rs.getString(4);
				int hit = rs.getInt(5);
				int group_no = rs.getInt(6);
				int order_no = rs.getInt(7);
				int depth = rs.getInt(8);
				int user_no = rs.getInt(9);
				String user_name = rs.getString(10);

				// mapping
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setRegDate(reg_date);
				vo.setHit(hit);
				vo.setGroupNo(group_no);
				vo.setOrderNo(order_no);
				vo.setDepth(depth);
				vo.setUserNo(user_no);
				vo.setUserName(user_name);

				result.add(vo);
			}

		} catch (SQLException e) {
			// 2. 관련 : linux 꺼져있을 때 등등 connection안될 때
			System.out.println("error :" + e);
		} finally {
			// clean-up; 자원정리는 만들어진 순서 거꾸로 하기
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
				System.out.println("connection close error:" + e);
			}
		}
		return result;
	}
	public List<BoardVo> findByContents(String kwd) {
		List<BoardVo> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1,2 : driver loading, connection
			conn = new ForConnection().getConnection();

			// 3. prepare sql statement + binding
			String sql = "select b.no,title,contents,reg_date,hit,group_no,order_no,depth, u.no, u.name"
					+ " from board b"
					+ " join user u on u.no = b.user_no"
					+ " where contents like ? "
					+ " order by group_no desc , order_no asc";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+kwd+"%");

			// 4. execute
			rs = pstmt.executeQuery();

			// 6. result 가져오기
			while (rs.next() /* 각각의 행 가지고 오기 */) {
				long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				String reg_date = rs.getString(4);
				int hit = rs.getInt(5);
				int group_no = rs.getInt(6);
				int order_no = rs.getInt(7);
				int depth = rs.getInt(8);
				int user_no = rs.getInt(9);
				String user_name = rs.getString(10);

				// mapping
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setRegDate(reg_date);
				vo.setHit(hit);
				vo.setGroupNo(group_no);
				vo.setOrderNo(order_no);
				vo.setDepth(depth);
				vo.setUserNo(user_no);
				vo.setUserName(user_name);

				result.add(vo);
			}

		} catch (SQLException e) {
			// 2. 관련 : linux 꺼져있을 때 등등 connection안될 때
			System.out.println("error :" + e);
		} finally {
			// clean-up; 자원정리는 만들어진 순서 거꾸로 하기
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
				System.out.println("connection close error:" + e);
			}
		}
		return result;
	}
	public List<BoardVo> findByWriter(String kwd) {
		List<BoardVo> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1,2 : driver loading, connection
			conn = new ForConnection().getConnection();

			// 3. prepare sql statement + binding
			String sql = "select b.no,title,contents,reg_date,hit,group_no,order_no,depth, u.no, u.name"
					+ " from board b"
					+ " join user u on u.no = b.user_no"
					+ " where u.name like ? "
					+ " order by group_no desc , order_no asc";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+kwd+"%");

			// 4. execute
			rs = pstmt.executeQuery();

			// 6. result 가져오기
			while (rs.next() /* 각각의 행 가지고 오기 */) {
				long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				String reg_date = rs.getString(4);
				int hit = rs.getInt(5);
				int group_no = rs.getInt(6);
				int order_no = rs.getInt(7);
				int depth = rs.getInt(8);
				int user_no = rs.getInt(9);
				String user_name = rs.getString(10);

				// mapping
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setRegDate(reg_date);
				vo.setHit(hit);
				vo.setGroupNo(group_no);
				vo.setOrderNo(order_no);
				vo.setDepth(depth);
				vo.setUserNo(user_no);
				vo.setUserName(user_name);

				result.add(vo);
			}

		} catch (SQLException e) {
			// 2. 관련 : linux 꺼져있을 때 등등 connection안될 때
			System.out.println("error :" + e);
		} finally {
			// clean-up; 자원정리는 만들어진 순서 거꾸로 하기
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
				System.out.println("connection close error:" + e);
			}
		}
		return result;
	}

}
