package cn.org.fjiot.infusion.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.org.fjiot.infusion.entity.User;

public interface UserDao extends JpaRepository<User, Long> {

}
