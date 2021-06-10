package com.douzone.mysite.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;

@Controller("userControllerApi") //다른 패키지에 같은 이름의 controller가 있기에 HandlerMapping시 id가 두 컨트롤러가 똑같이 들어갈 수 있어서 id를 임의로 설정해줌
@RequestMapping("/user/api")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@ResponseBody
	@RequestMapping("/checkemail")
	public Object checkEmail(@RequestParam(value="email", required=true, defaultValue="") String email) {
		
		UserVo userVo = userService.getUser(email);
		
		Map<String,Object> map = new HashMap<>();
		map.put("result", "success"); //통신 결과 return
		map.put("exist", userVo != null);
		
		return map;
	}
	
}
