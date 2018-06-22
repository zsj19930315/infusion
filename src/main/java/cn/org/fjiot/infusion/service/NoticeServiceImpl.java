package cn.org.fjiot.infusion.service;

import java.sql.Timestamp;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.fjiot.infusion.dao.NoticeDao;
import cn.org.fjiot.infusion.entity.Notice;
import cn.org.fjiot.infusion.entity.User;
import cn.org.fjiot.infusion.enums.Visible;
import cn.org.fjiot.infusion.exception.NotFoundException;

@Service
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired
	private NoticeDao noticeDao;

	@Override
	public Notice getLast(Visible visible) throws NotFoundException {
		Notice n = noticeDao.findOneByVisibleOrderByPubTimeDesc(visible);
		if (null == n) {
			throw new NotFoundException("还未发布该类型公告，敬请期待");
		}
		return n;
	}

	@Override
	public Notice add(String title, String content, String image, Visible visible) {
		User u = (User) SecurityUtils.getSubject().getPrincipal();
		String author = u.getName();
		Long departmentId = u.getDepartmentId();
		Notice n = new Notice();
		n.setAuthor(author);
		n.setContent(content);
		n.setDepartmentId(departmentId);
		n.setImage(image);
		n.setPubTime(new Timestamp(System.currentTimeMillis()));
		n.setTitle(title);
		n.setVisible(visible);
		noticeDao.save(n);
		return n;
	}

	@Override
	public Notice delete(Long id) throws NotFoundException {
		Notice n = noticeDao.findById(id).get();
		if (null == n) {
			throw new NotFoundException("未找到指定公告");			
		}
		return n;
	}

	@Override
	public List<Notice> getList() {
		List<Notice> ns = noticeDao.findAll();
		return ns;
	}

}
