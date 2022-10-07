package com.fonada.masking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "holiday")
public class HolidayEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;

	@Column(name = "did_Number")
	private String didNo;
	@Column(name = "is_Global")
	private Integer isGlobal;
	@Column(name = "holiday_Date")
	private String holidayDate;
	@Column(name = "created_Date")
	private String createdDate;
	@Column(name = "created_By")
	private String createdBy;
	@Column(name = "modify_Date")
	private String modifyDate;
	@Column(name = "modify_By")
	private String modifyBy;
	@Column(name = "user_id")
	private Integer usersId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDidNo() {
		return didNo;
	}

	public void setDidNo(String didNo) {
		this.didNo = didNo;
	}

	public Integer getIsGlobal() {
		return isGlobal;
	}

	public void setIsGlobal(Integer isGlobal) {
		this.isGlobal = isGlobal;
	}

	public String getHolidayDate() {
		return holidayDate;
	}

	public void setHolidayDate(String holidayDate) {
		this.holidayDate = holidayDate;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Integer getUsersId() {
		return usersId;
	}

	public void setUsersId(Integer usersId) {
		this.usersId = usersId;
	}

	@Override
	public String toString() {
		return "HolidayEntity [id=" + id + ", didNo=" + didNo + ", isGlobal=" + isGlobal + ", holidayDate="
				+ holidayDate + ", createdDate=" + createdDate + ", createdBy=" + createdBy + ", modifyDate="
				+ modifyDate + ", modifyBy=" + modifyBy + ", usersId=" + usersId + "]";
	}

}
