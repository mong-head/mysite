package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.UserVo;

public class UserRepository {
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			// 1. JDBC driver loading
			// 		loading 후 Driver Class가 DriverManager만듦 - 우리가 new로 drivermanager만들 필요없음
			Class.forName("org.mariadb.jdbc.Driver");
			
			// 2. DriverManager - get connection
			String url = "jdbc:mysql://192.168.254.40:3307/webdb?characterEncoding=utf8"; //linux server ip
			conn	   = DriverManager.getConnection(url,"webdb","webdb");
			conn.createStatement();
			System.out.println("success connection");
		} catch (ClassNotFoundException e) {
			System.out.println("fail to load driver:"+e);
		}
		return conn;	

	}
	
	public boolean insert(UserVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			conn = getConnection();
			
			// 3. prepare Statement 
			String sql = "insert into user values(null,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			// 4. SQL binging
			pstmt.setString(1, vo.getName()); // parameter mapping
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			
			// 5. SQL execute
			int count = pstmt.executeUpdate();
			result = count == 1;
			
		} catch (SQLException e) {
			// 2. 관련 : linux 꺼져있을 때 등등 connection안될 때 
			System.out.println("error :"+e);
		} finally {
			//clean-up; 자원정리는 만들어진 순서 거꾸로 하기
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();					
				}
				
			} catch (SQLException e) {
				System.out.println("connection close error:"+e);
			}
		}
		return result;
	}

	public List<UserVo> findAll() {
		List<UserVo> result = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1,2 : driver loading, connection
			conn = getConnection();
			
			// 3. prepare sql statement
			String sql = "select no,name,password,message,reg_date"
					+ " from guestbook"
					+ " order by no desc";
			pstmt = conn.prepareStatement(sql);
			
			// 4. SQL실행 ( no binding )
			rs = pstmt.executeQuery();
			
			// 6. result 가져오기
			while(rs.next() /*각각의 행 가지고 오기*/) {
				long no = rs.getLong(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				String message = rs.getString(4);
				String reg_date = rs.getString(5);
				
				// mapping
				UserVo vo = new UserVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setPassword(password);
				
				result.add(vo);
			}
			
		} catch (SQLException e) {
			// 2. 관련 : linux 꺼져있을 때 등등 connection안될 때 
			System.out.println("error :"+e);
		} finally {
			//clean-up; 자원정리는 만들어진 순서 거꾸로 하기
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();					
				}
				
			} catch (SQLException e) {
				System.out.println("connection close error:"+e);
			}
		}
		return result;
	}
	public boolean delete(UserVo vo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			// 1,2 : driver loading, connection
			conn = getConnection();
			
			// 3. Statement 생성 - query 실행위함
			String sql = "delete from guestbook where no = ? and password = ?";
			pstmt = conn.prepareStatement(sql);
			
			// 4. binding
			pstmt.setLong(1, vo.getNo());
			pstmt.setString(2, vo.getPassword());
			
			// 5. SQL실행
			int count = pstmt.executeUpdate();
			result = count == 1;
			
		} catch (SQLException e) {
			// 2. 관련 : linux 꺼져있을 때 등등 connection안될 때 
			System.out.println("error :"+e);
		} finally {
			//clean-up; 자원정리는 만들어진 순서 거꾸로 하기
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();					
				}
				
			} catch (SQLException e) {
				System.out.println("connection close error:"+e);
			}
		}
		return result;
	}
	public String findPassword(UserVo vo) {
		String result = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1,2 : driver loading, connection
			conn = getConnection();
			
			// 3. prepare sql statement
			String sql = "select password"
					+ " from guestbook"
					+ " where no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, vo.getNo());
			
			// 4. SQL실행 
			rs = pstmt.executeQuery();
			
			// 6. result 가져오기
			while(rs.next() /*각각의 행 가지고 오기*/) {
				result = rs.getString(1);
			}
			
		} catch (SQLException e) {
			// 2. 관련 : linux 꺼져있을 때 등등 connection안될 때 
			System.out.println("error :"+e);
		} finally {
			//clean-up; 자원정리는 만들어진 순서 거꾸로 하기
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();					
				}
				
			} catch (SQLException e) {
				System.out.println("connection close error:"+e);
			}
		}
		return result;
	}

	//보안상 위험한 클래스 : password는 select로 받아오면 안됨
	public UserVo findByEmailAndPassword(String email, String password) {
		UserVo result = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1,2 : driver loading, connection
			conn = getConnection();
			
			// 3. prepare sql statement
			String sql = "select no,name from user where email=? and password=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			// 4. SQL실행 
			rs = pstmt.executeQuery();
			
			//결과는 1개이기에 while안쓰고 if씀
			if(rs.next()) {	
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				
				result = new UserVo();
				result.setNo(no);
				result.setName(name);
			}
			
		} catch (SQLException e) {
			// 2. 관련 : linux 꺼져있을 때 등등 connection안될 때 
			System.out.println("error :"+e);
		} finally {
			//clean-up; 자원정리는 만들어진 순서 거꾸로 하기
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();					
				}
				
			} catch (SQLException e) {
				System.out.println("connection close error:"+e);
			}
		}
		return result;
		
	}

	public UserVo findByNo(Long userNo) {
		
		UserVo result = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1,2 : driver loading, connection
			conn = getConnection();
			
			// 3. prepare sql statement
			String sql = "select no,email from user where no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, userNo);
			
			// 4. SQL실행 
			rs = pstmt.executeQuery();
			
			//결과는 1개이기에 while안쓰고 if씀
			if(rs.next()) {	
				Long no = rs.getLong(1);
				String email = rs.getString(2);
				
				result = new UserVo();
				result.setNo(no);
				result.setEmail(email);
			}
			
		} catch (SQLException e) {
			// 2. 관련 : linux 꺼져있을 때 등등 connection안될 때 
			System.out.println("error :"+e);
		} finally {
			//clean-up; 자원정리는 만들어진 순서 거꾸로 하기
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();					
				}
				
			} catch (SQLException e) {
				System.out.println("connection close error:"+e);
			}
		}
		return result;
	}

	public void update(UserVo vo) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1,2 : driver loading, connection
			conn = getConnection();
			
			// 3. prepare sql statement
			String sql = "update user set name = ?, password= ? , gender= ? where no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getGender());
			pstmt.setLong(4, vo.getNo());
			
			// 4. SQL실행 
			rs = pstmt.executeQuery();
			
		} catch (SQLException e) {
			// 2. 관련 : linux 꺼져있을 때 등등 connection안될 때 
			System.out.println("error :"+e);
		} finally {
			//clean-up; 자원정리는 만들어진 순서 거꾸로 하기
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();					
				}
				
			} catch (SQLException e) {
				System.out.println("connection close error:"+e);
			}
		}
	}
}
