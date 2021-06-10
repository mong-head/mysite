package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.service.FileUploadService;
import com.douzone.mysite.service.GalleryService;
import com.douzone.mysite.vo.GalleryVo;

@Controller
@RequestMapping("/gallery")
public class GalleryController {
	
	@Autowired
	private GalleryService galleryService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@RequestMapping("")
	public String index(Model model) {
		List<GalleryVo> list = galleryService.getImageList();
		model.addAttribute("list",list);
		return "gallery/index";
	}
	
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public String upload(@RequestParam("file1") MultipartFile file1, GalleryVo vo) {
		String url = fileUploadService.restore(file1);
		if(url == null) {
			return "redirect:/gallery";
		}
		vo.setUrl(url);
		galleryService.uploadImage(vo);
		return "redirect:/gallery";
	}
	
	@RequestMapping(value="/delete/{no}")
	public String delete(@PathVariable("no") Long no) {
		galleryService.deleteImage(no);
		return "redirect:/gallery";
	}
}
