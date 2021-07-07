package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	
	@Autowired // DI (의존성 주입)
	private GuestbookRepository guestbookRepository;
	
	public List<GuestbookVo> getMessageList() {
		return guestbookRepository.findAll();
	}
	
	public List<GuestbookVo> getMessageList(Long no/*기준*/) {
		return guestbookRepository.findAll(no);
	}
	
	public boolean deleteMessage(Long no, String password) {
		GuestbookVo vo = new GuestbookVo();
		vo.setNo(no);
		vo.setPassword(password);
		return guestbookRepository.delete(vo);
	}
	
	public boolean addMessage(GuestbookVo vo) {
		if(guestbookRepository.insert(vo)) {
			return true;
		}
		return false;
	}
	
}
