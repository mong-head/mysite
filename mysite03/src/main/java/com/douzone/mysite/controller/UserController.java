package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.security.AuthUser;
import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.SiteVo;
import com.douzone.mysite.vo.UserVo;

//@Auth
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	// joinform
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() {
		return "user/join";
	}

	// join
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(UserVo vo) {
		return "redirect:/user/joinsuccess";
	}

	// join
	@RequestMapping(value = "/joinsuccess")
	public String joinsuccess(UserVo vo) {
		return "user/joinsuccess";
	}

	// joinform
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}

	// AuthInterceptor, Auth사용으로 이 두 부분은 밖으로 빠져나감 
//	// login : 원래는 밖으로 나가야 하는 부분, 현재는 지식이 없어서 안에다가 넣음
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public String login(HttpSession session,
//			@RequestParam(value = "email", required = true, defaultValue = "") String email,
//			@RequestParam(value = "password", required = true, defaultValue = "") String password, Model model) {
//
//		UserVo authUser = userService.getUser(email, password);
//		if (authUser == null) {
//			model.addAttribute("result", "fail");
//			model.addAttribute("email", email);
//			return "user/login";
//		}
//		// login 처리 (인증 처리)
//		session.setAttribute("authUser", authUser);
//		return "redirect:/";
//	}

//	@RequestMapping("/logout")
//	public String logout(HttpSession session /* session기술이 이렇게 안으로 들어오면 별로 안 좋음 */) {
//		UserVo authUser = (UserVo) session.getAttribute("authUser");
//		// login안하고 logout으로 들어온 경우 : 접근제어
//		if (authUser == null) {
//			return "redirect:/";
//		}
//
//		// logout 처리
//		session.removeAttribute("authUser");
//		session.invalidate();
//		return "redirect:/";
//	}

	@Auth
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(@AuthUser UserVo authUser , Model model) {
		//AuthUser사용으로 이거 필요없어짐
//		UserVo authUser = (UserVo) session.getAttribute("authUser");
//		// login안하고 update으로 들어온 경우 : 접근제어
//		if (authUser == null) {
//			return "redirect:/";
//		}
		
		Long no = authUser.getNo();
		UserVo userVo = userService.getUser(no);
		model.addAttribute("userVo", userVo);
		
		return "user/update";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@AuthUser UserVo authUser , UserVo userVo) {
//		UserVo authUser = (UserVo) session.getAttribute("authUser");
//		// login안하고 update으로 들어온 경우 : 접근제어
//		if (authUser == null) {
//			return "redirect:/";
//		}
		
		userVo.setNo(authUser.getNo());
		userService.updateUser(userVo);
		authUser.setName(userVo.getName());

		return "redirect:/";
	}
}
