package com.fonada.masking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "did_blocked_number")
public class DidBlockedNumberEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;
	@Column(name = "cli_Number")
	private String cliNo;
	@Column(name = "did_Number")
	private String didNo;
	@Column(name = "is_Active")
	private String isActive;
	@Column(name = "is_Global")
	private Integer isGlobal;
	@Column(name = "dnd")
	private Integer dnd;
	@Column(name = "created_Date")
	private String createdDate;
	@Column(name = "created_By")
	private String createdBy;
	@Column(name = "modify_Date")
	private String modifyDate;
	@Column(name = "modify_By")
	private String modifyBy;
	private Integer usersId;
	// private Users usersByUsersId;

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

	@Column(name = "users_id")
	public Integer getUsersId() {
		return usersId;
	}

	public void setUsersId(Integer usersId) {
		this.usersId = usersId;
	}

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "users_id", referencedColumnName = "id", nullable = true,
	 * insertable = false, updatable = false) public Users getUsersByUsersId() {
	 * return usersByUsersId; }
	 * 
	 * public void setUsersByUsersId(Users usersByUsersId) { this.usersByUsersId =
	 * usersByUsersId; }
	 */

	/*
	 * @Column(name = "reason_Id") private Long reasonId;
	 * 
	 * public Long getReasonId() { return reasonId; }
	 * 
	 * public void setReasonId(Long reasonId) { this.reasonId = reasonId; }
	 */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCliNo() {
		return cliNo;
	}

	public void setCliNo(String cliNo) {
		this.cliNo = cliNo;
	}

	public String getDidNo() {
		return didNo;
	}

	public void setDidNo(String didNo) {
		this.didNo = didNo;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Integer getIsGlobal() {
		return isGlobal;
	}

	public void setIsGlobal(Integer isGlobal) {
		this.isGlobal = isGlobal;
	}

	public Integer getDnd() {
		return dnd;
	}

	public void setDnd(Integer dnd) {
		this.dnd = dnd;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "DidBlockedNumberEntity [id=" + id + ", cliNo=" + cliNo + ", didNo=" + didNo + ", isActive=" + isActive
				+ ", isGlobal=" + isGlobal + ", dnd=" + dnd + ", createdDate=" + createdDate + ", createdBy="
				+ createdBy + ", modifyDate=" + modifyDate + ", modifyBy=" + modifyBy + ", usersId=" + usersId + "]";
	}

}
