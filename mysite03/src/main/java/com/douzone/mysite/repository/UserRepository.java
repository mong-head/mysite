package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.UserVo;

@Repository
public class UserRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public boolean insert(UserVo vo) {
		int count = sqlSession.insert("user.insert",vo);
		return count == 1;
	}

	public UserVo findByEmailAndPassword(String email, String password) {
		Map<String,String> map = new HashMap<>();
		map.put("email", email);
		map.put("password", password);
		return sqlSession.selectOne("user.findByEmailAndPassword",map);//하나만 결과로 나와야 함. 아니면 에러생김
	}

	public UserVo findByNo(Long userNo) {
		return sqlSession.selectOne("user.findByNo",userNo);
	}

	public boolean update(UserVo vo) {
		int count = sqlSession.update("user.update",vo);
		return count == 1;
	}
}
