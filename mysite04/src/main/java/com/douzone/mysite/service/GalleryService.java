package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GalleryRepository;
import com.douzone.mysite.vo.GalleryVo;

@Service
public class GalleryService {

	@Autowired
	private GalleryRepository galleryRepository;

	public List<GalleryVo> getImageList() {
		return galleryRepository.findAll();
	}

	public boolean uploadImage(GalleryVo vo) {
		return galleryRepository.insert(vo);
	}

	public boolean deleteImage(Long no) {
		return galleryRepository.delete(no);
	}
}
