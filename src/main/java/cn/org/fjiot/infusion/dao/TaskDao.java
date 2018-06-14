package cn.org.fjiot.infusion.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.org.fjiot.infusion.entity.Task;

public interface TaskDao extends JpaRepository<Task, Long> {

}
