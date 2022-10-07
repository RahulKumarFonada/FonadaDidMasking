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
@Table(name = "Users")
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "user_name")
	private String username;

	@Basic
	@Column(name = "password")
	private String password;

	@Basic
	@Column(name = "passwordsalt")
	private String passwordsalt;

	@Basic
	@Column(name = "first_name")
	private String firstname;

	@Basic
	@Column(name = "last_name")
	private String lastname;

	@Basic
	@Column(name = "pwd_reset_date")
	private String pwdResetDate;

	@Basic
	@Column(name = "email")
	private String email;

	@Basic
	@Column(name = "phone_number")
	private String phoneNumber;

	@Basic
	@Column(name = "role_id")
	private Integer roleId;

	@ManyToOne
	@JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Roles rolesByRolesId;

	/*
	 * @JsonFormat(pattern = Constants.defaultDateTimeFormat, timezone =
	 * Constants.defaultTimezone)
	 * 
	 * @Basic
	 */
	@Column(name = "user_lastlogin_datetime")
	private String userLastloginDatetime;

	@Column(name = "modified_date")
	private String modifiedDate;
	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private String createdDate;
	@Column(name = "is_deleted")
	private String isDeleted;

	@Column(name = "is_active")
	public String isActive;
	@Column(name = "modified_by")
	private String modifiedBy;

	public String getUserLastloginDatetime() {
		return userLastloginDatetime;
	}

	public void setUserLastloginDatetime(String userLastloginDatetime) {
		this.userLastloginDatetime = userLastloginDatetime;
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

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Roles getRolesByRolesId() {
		return rolesByRolesId;
	}

	public void setRolesByRolesId(Roles rolesByRolesId) {
		this.rolesByRolesId = rolesByRolesId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordsalt() {
		return passwordsalt;
	}

	public void setPasswordsalt(String passwordsalt) {
		this.passwordsalt = passwordsalt;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPwdResetDate() {
		return pwdResetDate;
	}

	public void setPwdResetDate(String pwdResetDate) {
		this.pwdResetDate = pwdResetDate;
	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", username=" + username + ", password=" + password + ", passwordsalt="
				+ passwordsalt + ", firstname=" + firstname + ", lastname=" + lastname + ", pwdResetDate="
				+ pwdResetDate + ", email=" + email + ", phoneNumber=" + phoneNumber + ", roleId=" + roleId
				+ ", rolesByRolesId=" + rolesByRolesId + ", userLastloginDatetime=" + userLastloginDatetime
				+ ", modifiedDate=" + modifiedDate + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", isDeleted=" + isDeleted + ", isActive=" + isActive + ", modifiedBy=" + modifiedBy + "]";
	}

}
