package cn.org.fjiot.infusion.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import cn.org.fjiot.infusion.entity.Notice;
import cn.org.fjiot.infusion.entity.more.NoticeMore;
import cn.org.fjiot.infusion.enums.Visible;

public interface NoticeDao extends JpaRepository<Notice, Long> {
	
	Notice findOneByVisibleOrderByPubTimeDesc(Visible visible);
	
//	Page<NoticeMore> findAll(Pageable pageable);

}
