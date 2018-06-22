package cn.org.fjiot.infusion.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.org.fjiot.infusion.entity.Notice;
import cn.org.fjiot.infusion.enums.Visible;
import cn.org.fjiot.infusion.exception.NotFoundException;
import cn.org.fjiot.infusion.service.NoticeService;
import cn.org.fjiot.infusion.util.Json;

@RestController
@RequestMapping("/notice")
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	private static final String DESK = "C:\\Users\\Administrator\\Desktop";
	
	@RequestMapping(value = "/getLast", method = RequestMethod.POST)
	public Json getLast(Visible visible) {
		Notice n;
		try {
			n = noticeService.getLast(visible);
		} catch (NotFoundException e) {
			return new Json(false, e.getMessage());
		}
		return new Json(true, "请求成功", n);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Json add(MultipartFile file, Visible visible, String title, String content) throws IOException {
		String image;
		if (null == file) {
			image = null;
		} else {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(DESK, "test");
			image = Files.write(path, bytes).toString();
		}
		noticeService.add(title, content, image, visible);
		return new Json(true, "添加成功");
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Json delete(Long id) {
		try {
			noticeService.delete(id);
		} catch (NotFoundException e) {
			return new Json(false, e.getMessage());
		}
		return new Json(true, "删除成功");
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public Json list() {
		return null;
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public void test(MultipartFile i) throws IOException {
		System.out.println(i);
		if (null == i) {
			return;
		}
		byte[] bytes = i.getBytes();
		Path path = Paths.get(DESK, String.format("%s.png", i.getOriginalFilename()));
		Path path2 = Files.write(path, bytes);
		System.out.println("path=" + path2);
	}

}
