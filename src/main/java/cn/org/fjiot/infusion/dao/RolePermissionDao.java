package cn.org.fjiot.infusion.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.org.fjiot.infusion.entity.RolePermission;

public interface RolePermissionDao extends JpaRepository<RolePermission, Long> {

}
