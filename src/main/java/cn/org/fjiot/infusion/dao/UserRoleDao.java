package cn.org.fjiot.infusion.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.org.fjiot.infusion.entity.UserRole;

public interface UserRoleDao extends JpaRepository<UserRole, Long> {

}
