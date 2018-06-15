package cn.org.fjiot.infusion.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.org.fjiot.infusion.exception.ExistedException;
import cn.org.fjiot.infusion.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public String regist(String username, String password) {
		try {
			userService.regist(username, password);
		} catch (ExistedException e) {
			LOGGER.error(e.getMessage());
			return "注册失败";
		}
		return "注册成功";
	}
	
	@RequestMapping("/toLogin")
	public ModelAndView toLogin() {
		ModelAndView mav = new ModelAndView("userlogin");
		return mav;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String username, String password) {
		UsernamePasswordToken upt = new UsernamePasswordToken(username, password);
		Subject s = SecurityUtils.getSubject();
		try {			
			s.login(upt);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return "登录失败";
		}
		return "登录成功";
	}

}
