package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardRepository {

	@Autowired
	private SqlSession sqlSession;

	public boolean Insert(BoardVo vo) {
		int count = sqlSession.insert("board.insert", vo);
		return count == 1;
	}

	public boolean delete(BoardVo vo) {
		int count = sqlSession.delete("board.delete", vo);
		return count == 1;
	}

	public boolean update(BoardVo vo) {
		int count = sqlSession.update("board.update",vo);
		return count == 1;
	}

	public boolean updateHit(BoardVo vo) {
		int count = sqlSession.update("board.updateHit", vo);
		return count == 1;
	}

	public boolean updateOrderNo(BoardVo currentVo ) {
		int count = sqlSession.update("board.updateOrderNo", currentVo);
		return count == 1;
	}

	public List<BoardVo> findAll() {
		return sqlSession.selectList("board.findAll");
	}

	
	public List<BoardVo> findByPage(String kwd, String looking_for, int currentPageNo) {
		Map<String,Object> map = new HashMap<>();
		map.put("kwd", kwd);
		map.put("currentPageNo", currentPageNo);
		map.put("looking_for", looking_for);
		return sqlSession.selectList("board.findByPage", map);
	}

	public int CountArticle(String kwd, String looking_for) {
		Map<String,Object> map = new HashMap<>();
		map.put("kwd", kwd);
		map.put("looking_for", looking_for);
		return sqlSession.selectOne("board.countArticle",map);
	}

	public BoardVo findByNo(long no) {
		return sqlSession.selectOne("board.findByNo", no);
	}

}
