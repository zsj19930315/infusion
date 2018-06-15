package cn.org.fjiot.infusion.service;

import cn.org.fjiot.infusion.entity.User;
import cn.org.fjiot.infusion.exception.ExistedException;
import cn.org.fjiot.infusion.exception.NotFoundException;

public interface UserService {
	
	User regist(String username, String password) throws ExistedException;

	User findOneByUsername(String username) throws NotFoundException;

}
