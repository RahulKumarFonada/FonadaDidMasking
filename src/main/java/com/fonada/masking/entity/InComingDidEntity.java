package com.fonada.masking.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "incoming_did_no")
public class InComingDidEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	public Long id;
	@Column(name = "context")
	public String context;
	@Column(name = "exten")
	public String exten;
	@Column(name = "priority")
	public String priority;
	@Column(name = "app")
	public String app;
	@Column(name = "appData")
	public String appData;
	private int usersId;
	private String createdDate;
	private String isActive;
	private String modifiedBy;
	private String modifiedDate;
	private String createdBy;

	@Basic
	@Column(name = "users_id")
	public int getUsersId() {
		return usersId;
	}

	public void setUsersId(int usersId) {
		this.usersId = usersId;
	}

	/*
	 * private Users usersByUsersId;
	 * 
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "users_id", referencedColumnName = "id", nullable = true,
	 * insertable = false, updatable = false) public Users getUsersByUsersId() {
	 * return usersByUsersId; }
	 * 
	 * public void setUsersByUsersId(Users usersByUsersId) { this.usersByUsersId =
	 * usersByUsersId; }
	 */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getExten() {
		return exten;
	}

	public void setExten(String exten) {
		this.exten = exten;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getAppData() {
		return appData;
	}

	public void setAppData(String appData) {
		this.appData = appData;
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
