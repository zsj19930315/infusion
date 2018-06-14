package cn.org.fjiot.infusion.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sj_device")
public class Device implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String electric;
	
	private Boolean isLowElec;
	
	private Boolean isStart;
	
	private Boolean isLose;
	
	private Timestamp activeTime;
	
	private String roomBed;
	
	private Long taskId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getElectric() {
		return electric;
	}

	public void setElectric(String electric) {
		this.electric = electric;
	}

	public Boolean getIsLowElec() {
		return isLowElec;
	}

	public void setIsLowElec(Boolean isLowElec) {
		this.isLowElec = isLowElec;
	}

	public Boolean getIsStart() {
		return isStart;
	}

	public void setIsStart(Boolean isStart) {
		this.isStart = isStart;
	}

	public Boolean getIsLose() {
		return isLose;
	}

	public void setIsLose(Boolean isLose) {
		this.isLose = isLose;
	}

	public Timestamp getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Timestamp activeTime) {
		this.activeTime = activeTime;
	}

	public String getRoomBed() {
		return roomBed;
	}

	public void setRoomBed(String roomBed) {
		this.roomBed = roomBed;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

}
