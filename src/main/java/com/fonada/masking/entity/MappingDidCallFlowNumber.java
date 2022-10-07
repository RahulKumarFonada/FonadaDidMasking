package com.fonada.masking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

@Entity
@Table(name = MappingDidCallFlowNumber.TABLE_NAME)
public class MappingDidCallFlowNumber {
	public static final String TABLE_NAME = "mapping_did_call_flow_number";

	private Long id;
	private String didNumber;
	private Integer preferredLoc;
	private String mappingNumber;
	private Integer dnd;
	private Integer numberType;
	private Integer usersId;
	private Users usersByUsersId;
	private String createdDate;
	private String isActive;
	private String modifiedBy;
	private String modifiedDate;
	private String createdBy;
	@Formula(value = "(SELECT COUNT(p) FROM " + MappingDidCallFlowNumber.TABLE_NAME
			+ " p WHERE p.didNumber = didNumber)")
	private Integer didMappingCount;

	public Integer getDidMappingCount() {
		return didMappingCount;
	}

	public void setDidMappingCount(Integer didMappingCount) {
		this.didMappingCount = didMappingCount;
	}

	@Column(name = "users_id")
	public Integer getUsersId() {
		return usersId;
	}

	public void setUsersId(Integer usersId) {
		this.usersId = usersId;
	}

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
	@Column(name = "Id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getDidNumber() {
		return didNumber;
	}

	public void setDidNumber(String didNumber) {
		this.didNumber = didNumber;
	}

	public Integer getPreferredLoc() {
		return preferredLoc;
	}

	public void setPreferredLoc(Integer preferredLoc) {
		this.preferredLoc = preferredLoc;
	}

	public String getMappingNumber() {
		return mappingNumber;
	}

	public void setMappingNumber(String mappingNumber) {
		this.mappingNumber = mappingNumber;
	}

	public Integer getDnd() {
		return dnd;
	}

	public void setDnd(Integer dnd) {
		this.dnd = dnd;
	}

	public Integer getNumberType() {
		return numberType;
	}

	public void setNumberType(Integer numberType) {
		this.numberType = numberType;
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

}
