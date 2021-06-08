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

	public Map<String,Object> getMessageList(int currentPageNo,String looking_for,String kwd) {

		List<BoardVo> list = boardRepository.findByPage(kwd, looking_for, (currentPageNo-1)*5);
		int size = boardRepository.CountArticle(kwd,looking_for);

		Map<String,Object> map = new HashMap<>();
		map.put("list", list);
		map.put("size", size);
		
		return map;
	}

}
