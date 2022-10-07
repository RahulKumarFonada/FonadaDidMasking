package com.fonada.masking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "off_hour")
public class OffHourManagement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;
	@Column(name = "is_Global")
	private Integer isGlobal;
	@Column(name = "offhour_start_Date")
	private String offHourstartDate;
	@Column(name = "offhour_end_Date")
	private String offHourEndDate;
	@Column(name = "description")
	private String description;
	@Column(name = "createdDate")
	private String createdDate;
	@Column(name = "created_By")
	private String createdBy;
	@Column(name = "modify_Date")
	private String modifyDate;
	@Column(name = "modify_By")
	private String modifyBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIsGlobal() {
		return isGlobal;
	}

	public void setIsGlobal(Integer isGlobal) {
		this.isGlobal = isGlobal;
	}

	public String getOffHourstartDate() {
		return offHourstartDate;
	}

	public void setOffHourstartDate(String offHourstartDate) {
		this.offHourstartDate = offHourstartDate;
	}

	public String getOffHourEndDate() {
		return offHourEndDate;
	}

	public void setOffHourEndDate(String offHourEndDate) {
		this.offHourEndDate = offHourEndDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@Override
	public String toString() {
		return "OffHourManagement [id=" + id + ", isGlobal=" + isGlobal + ", offHourstartDate=" + offHourstartDate
				+ ", offHourEndDate=" + offHourEndDate + ", description=" + description + ", createdDate=" + createdDate
				+ ", createdBy=" + createdBy + ", modifyDate=" + modifyDate + ", modifyBy=" + modifyBy + "]";
	}

}
