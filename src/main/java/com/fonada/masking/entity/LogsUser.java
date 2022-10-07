package com.fonada.masking.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "logs_user")
public class LogsUser {
	private int id;
	private int usersId;
	private String fileName;
	private Integer totalCount;
	private Integer validRecord;
	private Integer invalidRecord;
	private String createdDate;

	private Users usersByUsersId;

	@ManyToOne
	@JoinColumn(name = "users_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false)
	public Users getUsersByUsersId() {
		return usersByUsersId;
	}

	public void setUsersByUsersId(Users usersByUsersId) {
		this.usersByUsersId = usersByUsersId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getValidRecord() {
		return validRecord;
	}

	public void setValidRecord(Integer validRecord) {
		this.validRecord = validRecord;
	}

	public Integer getInvalidRecord() {
		return invalidRecord;
	}

	public void setInvalidRecord(Integer invalidRecord) {
		this.invalidRecord = invalidRecord;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "users_id")
	public int getUsersId() {
		return usersId;
	}

	public void setUsersId(int usersId) {
		this.usersId = usersId;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	@Basic
	@Column(name = "file_name")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

}
