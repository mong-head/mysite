package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.exception.GuestbookRepositoryException;
import com.douzone.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private DataSource dataSource;
	
	public boolean insert(GuestbookVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			conn = dataSource.getConnection();
			
			// 3. prepare Statement 
			String sql = "insert into guestbook values( null,?,?,?,now())";
			pstmt = conn.prepareStatement(sql);
			
			// 4. SQL binging
			pstmt.setString(1, vo.getName()); // parameter mapping
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getMessage());
			
			// 5. SQL execute
			int count = pstmt.executeUpdate();
			result = count == 1;
			
		} catch (SQLException e) {
			// 2. 관련 : linux 꺼져있을 때 등등 connection안될 때 
			throw new GuestbookRepositoryException(e.getMessage());
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

	public List<GuestbookVo> findAll() {
		return sqlSession.selectList("guestbook.findAll");
	}
	public boolean delete(GuestbookVo vo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			// 1,2 : driver loading, connection
			conn = dataSource.getConnection();
			
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
			new GuestbookRepositoryException(e.getMessage());
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
	public String findPassword(GuestbookVo vo) {
		String result = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1,2 : driver loading, connection
			conn = dataSource.getConnection();
			
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
			throw new GuestbookRepositoryException(e.getMessage());
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
}
