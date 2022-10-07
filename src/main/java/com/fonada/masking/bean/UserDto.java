package com.fonada.masking.bean;

import java.io.Serializable;

public class UserDto implements Serializable{
	private String userName;
	private String password;
	private String fristName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private Integer roleId;
    
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFristName() {
		return fristName;
	}

	public void setFristName(String fristName) {
		this.fristName = fristName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "UserDto [userName=" + userName + ", password=" + password + ", fristName=" + fristName + ", lastName="
				+ lastName + ", email=" + email + ", phoneNumber=" + phoneNumber + ", roleId=" + roleId + "]";
	}

}
