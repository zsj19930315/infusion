package cn.org.fjiot.infusion.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.org.fjiot.infusion.entity.Notice;

public interface NoticeDao extends JpaRepository<Notice, Long> {

}
