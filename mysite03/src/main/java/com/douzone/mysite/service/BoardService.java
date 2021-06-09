package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	public Map<String, Object> getMessageList(int currentPageNo, String looking_for, String kwd) {

		List<BoardVo> list = boardRepository.findByPage(kwd, looking_for, (currentPageNo - 1) * 5);
		int size = boardRepository.CountArticle(kwd, looking_for);
		
		// page parameters
		int totalPage = (int) Math.ceil(size / 5f);
		int firstPageNo = currentPageNo - (currentPageNo - 1) % 5;
		int lastPageNo = firstPageNo + 4;
		int nextPageNo = lastPageNo + 1;
		int prevPageNo = firstPageNo - 1;
		if (prevPageNo < 1) {
			prevPageNo = 1;
		}
		if (nextPageNo > totalPage) {
			nextPageNo = totalPage;
		}
		
		Map<String,Integer> pageInfo = new HashMap<>();
		pageInfo.put("currentPageNo", currentPageNo);
		pageInfo.put("totalPage", totalPage);
		pageInfo.put("firstPageNo", firstPageNo);
		pageInfo.put("lastPageNo", lastPageNo);
		pageInfo.put("nextPageNo", nextPageNo);
		pageInfo.put("prevPageNo", prevPageNo);
		
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("size", size);
		map.put("pageInfo", pageInfo);

		return map;
	}

	public BoardVo getArticle(long no) {
		BoardVo boardVo = boardRepository.findByNo(no);
		boardVo.setNo(no);
		boardVo.setHit(boardVo.getHit() + 1);
		boardRepository.updateHit(boardVo);
		return boardVo;
	}

	public boolean deleteArticle(long no) {
		BoardVo vo = new BoardVo();
		vo.setNo(no);
		return boardRepository.delete(vo);
	}

}
