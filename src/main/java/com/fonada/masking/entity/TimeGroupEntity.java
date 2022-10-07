package com.fonada.masking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "time_Group")
public class TimeGroupEntity {
	private Long id;
	private String timeGroupName;
	private String timeGroup;
	private String createdDate;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTimeGroupName() {
		return timeGroupName;
	}

	public void setTimeGroupName(String timeGroupName) {
		this.timeGroupName = timeGroupName;
	}

	public String getTimeGroup() {
		return timeGroup;
	}

	public void setTimeGroup(String timeGroup) {
		this.timeGroup = timeGroup;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "TimeGroupEntity [id=" + id + ", timeGroupName=" + timeGroupName + ", timeGroup=" + timeGroup
				+ ", createdDate=" + createdDate + "]";
	}

}
