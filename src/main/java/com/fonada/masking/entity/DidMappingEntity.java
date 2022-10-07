package com.fonada.masking.entity;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "did_mapping")
public class DidMappingEntity {
	public String didNumber;
	public Integer didStatus;
	public String didCli;
	public String callRecording;
	public String holidayCheck;
	public String timeGroupId;
	public String offtimeDestinationType;
	public String offtimeDestinationTypeValue;
	public String holidayDestinationType;
	public String holidayDestinationTypeValue;
	public Integer ringTime;
	private Long id;
	private int usersId;
	private Users usersByUsersId;
	private String createdDate;
	private String isActive;
	private String modifiedBy;
	private String modifiedDate;
	private String createdBy;
	private List<MappingDidCallFlowNumber> mappingDidCallFlowNumber;

	

	@ManyToOne
	@JoinColumn(name = "users_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false)
	public Users getUsersByUsersId() {
		return usersByUsersId;
	}
	
    @OneToMany(mappedBy = "didNumber",targetEntity = MappingDidCallFlowNumber.class,fetch = FetchType.EAGER)
	public List<MappingDidCallFlowNumber> getMappingDidCallFlowNumber() {
		return mappingDidCallFlowNumber;
	}

	public void setMappingDidCallFlowNumber(List<MappingDidCallFlowNumber> mappingDidCallFlowNumber) {
		this.mappingDidCallFlowNumber = mappingDidCallFlowNumber;
	}

	public void setUsersByUsersId(Users usersByUsersId) {
		this.usersByUsersId = usersByUsersId;
	}

	@Basic
	@Column(name = "users_id")
	public int getUsersId() {
		return usersId;
	}

	public void setUsersId(int usersId) {
		this.usersId = usersId;
	}

	@Column(name = "did_number")
	public String getDidNumber() {
		return didNumber;
	}

	public void setDidNumber(String didNumber) {
		this.didNumber = didNumber;
	}

	public String getHolidayDestinationType() {
		return holidayDestinationType;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDidStatus() {
		return didStatus;
	}

	public void setDidStatus(Integer didStatus) {
		this.didStatus = didStatus;
	}

	public String getDidCli() {
		return didCli;
	}

	public void setDidCli(String didCli) {
		this.didCli = didCli;
	}

	public String getCallRecording() {
		return callRecording;
	}

	public void setCallRecording(String callRecording) {
		this.callRecording = callRecording;
	}

	public String getHolidayCheck() {
		return holidayCheck;
	}

	public void setHolidayCheck(String holidayCheck) {
		this.holidayCheck = holidayCheck;
	}

	public String getTimeGroupId() {
		return timeGroupId;
	}

	public void setTimeGroupId(String timeGroupId) {
		this.timeGroupId = timeGroupId;
	}

	public String getOfftimeDestinationType() {
		return offtimeDestinationType;
	}

	public void setOfftimeDestinationType(String offtimeDestinationType) {
		this.offtimeDestinationType = offtimeDestinationType;
	}

	public String getOfftimeDestinationTypeValue() {
		return offtimeDestinationTypeValue;
	}

	public void setOfftimeDestinationTypeValue(String offtimeDestinationTypeValue) {
		this.offtimeDestinationTypeValue = offtimeDestinationTypeValue;
	}

	public String setHolidayDestinationType() {
		return holidayDestinationType;
	}

	public void setHolidayDestinationType(String holidayDestinationType) {
		this.holidayDestinationType = holidayDestinationType;
	}

	public String getHolidayDestinationTypeValue() {
		return holidayDestinationTypeValue;
	}

	public void setHolidayDestinationTypeValue(String holidayDestinationTypeValue) {
		this.holidayDestinationTypeValue = holidayDestinationTypeValue;
	}

	public Integer getRingTime() {
		return ringTime;
	}

	public void setRingTime(Integer ringTime) {
		this.ringTime = ringTime;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}
