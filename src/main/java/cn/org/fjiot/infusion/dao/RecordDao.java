package cn.org.fjiot.infusion.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.org.fjiot.infusion.entity.Record;

public interface RecordDao extends JpaRepository<Record, Long> {

}
