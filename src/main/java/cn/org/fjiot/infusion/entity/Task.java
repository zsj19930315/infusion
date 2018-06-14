package cn.org.fjiot.infusion.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sj_task")
public class Task implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Timestamp startTime;
	
	private Timestamp updateTime;
	
	private Timestamp endTime;
	
	private Timestamp restTime;
	
	private Long weightSum;
	
	private Long weightThreshold;
	
	private Long weightCur;
	
	private Boolean isStart;
	
	private Boolean isError;
	
	private Boolean isLowLevel;
	
	private Boolean isPercent;
	
	private Boolean isSuspend;
	
	private Long deviceId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Timestamp getRestTime() {
		return restTime;
	}

	public void setRestTime(Timestamp restTime) {
		this.restTime = restTime;
	}

	public Long getWeightSum() {
		return weightSum;
	}

	public void setWeightSum(Long weightSum) {
		this.weightSum = weightSum;
	}

	public Long getWeightThreshold() {
		return weightThreshold;
	}

	public void setWeightThreshold(Long weightThreshold) {
		this.weightThreshold = weightThreshold;
	}

	public Long getWeightCur() {
		return weightCur;
	}

	public void setWeightCur(Long weightCur) {
		this.weightCur = weightCur;
	}

	public Boolean getIsStart() {
		return isStart;
	}

	public void setIsStart(Boolean isStart) {
		this.isStart = isStart;
	}

	public Boolean getIsError() {
		return isError;
	}

	public void setIsError(Boolean isError) {
		this.isError = isError;
	}

	public Boolean getIsLowLevel() {
		return isLowLevel;
	}

	public void setIsLowLevel(Boolean isLowLevel) {
		this.isLowLevel = isLowLevel;
	}

	public Boolean getIsPercent() {
		return isPercent;
	}

	public void setIsPercent(Boolean isPercent) {
		this.isPercent = isPercent;
	}

	public Boolean getIsSuspend() {
		return isSuspend;
	}

	public void setIsSuspend(Boolean isSuspend) {
		this.isSuspend = isSuspend;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

}
