package cn.org.fjiot.infusion.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.org.fjiot.infusion.entity.Device;

public interface DeviceDao extends JpaRepository<Device, Long> {

}
