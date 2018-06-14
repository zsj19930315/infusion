package cn.org.fjiot.infusion.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.org.fjiot.infusion.entity.Permission;

public interface PermissionDao extends JpaRepository<Permission, Long> {

}
