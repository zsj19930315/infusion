package cn.org.fjiot.infusion.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.org.fjiot.infusion.exception.ExistedException;
import cn.org.fjiot.infusion.service.UserService;
import cn.org.fjiot.infusion.util.Json;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	private static final String UPLOADED_FOLDER = "D:\\";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ServletRequest request;
	
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public Json regist(String username, String password) {
		try {
			userService.regist(username, password);
		} catch (ExistedException e) {
			LOGGER.error(e.getMessage());
			return new Json(false, e.getMessage());
		}
		return new Json(true, "注册成功");
	}
	
	@RequestMapping("/toLogin")
	public ModelAndView toLogin() {
		ModelAndView mav = new ModelAndView("userlogin");
		return mav;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Json login(String username, String password) {
		LOGGER.warn("测试用");
		UsernamePasswordToken upt = new UsernamePasswordToken(username, password);
		Subject s = SecurityUtils.getSubject();
		try {	
			s.login(upt);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new Json(false, e.getMessage());
		}
		return new Json(true, "登录成功");
	}
	
	@RequestMapping("/u")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("upload");
		return mav;
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ModelAndView singleFileUpload(MultipartFile file, String title, String content, RedirectAttributes redirectAttributes) throws IOException {
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return new ModelAndView("redirect:show");
		}
		byte[] bs1 = file.getBytes();
		String ofn = file.getOriginalFilename();
		String path1 = ResourceUtils.getURL("classpath:").getPath();
		Path p = Paths.get(request.getServletContext().getRealPath("/static/upload"), ofn);
		Path p2 = Files.write(p, bs1);
		try {
			byte[] bs = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER, file.getOriginalFilename());
			Files.write(path, bs);
			redirectAttributes.addFlashAttribute("message", "you success" + file.getOriginalFilename());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			redirectAttributes.addFlashAttribute("message", "error");
		}
		return new ModelAndView("redirect:show");
	}

}
