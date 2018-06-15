package cn.org.fjiot.infusion.service;

import cn.org.fjiot.infusion.dao.UserDao;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.fjiot.infusion.entity.User;
import cn.org.fjiot.infusion.exception.ExistedException;
import cn.org.fjiot.infusion.exception.NotFoundException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public User regist(String username, String password) throws ExistedException {
		User u = userDao.findOneByUsername(username);
		if (null != u) {
			throw new ExistedException("用户" + username + "已存在，请重新输入");
		}
		String algorithmName = "md5";
		Integer hashIterations = 2;
		String salt = new SecureRandomNumberGenerator().nextBytes().toString();
		String newP = new SimpleHash(algorithmName, password, salt, hashIterations).toString();
		u = new User();
		u.setUsername(username);
		u.setPassword(newP);
		u.setSalt(salt);
		u = userDao.save(u);
		return u;
	}

	@Override
	public User findOneByUsername(String username) throws NotFoundException {
		User u = userDao.findOneByUsername(username);
		if (null == u) {
			throw new NotFoundException("用户" + username + "不存在，请确认信息");
		}
		return u;
	}

}
