package cn.org.fjiot.infusion.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.org.fjiot.infusion.entity.Hospital;

public interface HospitalDao extends JpaRepository<Hospital, Long> {

}
