package cn.org.fjiot.infusion.service;

import java.util.List;

import cn.org.fjiot.infusion.entity.Notice;
import cn.org.fjiot.infusion.enums.Visible;
import cn.org.fjiot.infusion.exception.NotFoundException;

public interface NoticeService {
	
	public Notice getLast(Visible visible) throws NotFoundException;
	
	public Notice add(String title, String content, String image, Visible visible);
	
	public Notice delete(Long id) throws NotFoundException;
	
	public List<Notice> getList();

}
