package com.fonada.masking.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Roles")
public class Roles {
	private int id;
	// private String code;
	private String name;
	private String description;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	public int getId() {
		return id;
	}

	@Embedded
	private CommonFields commonFields;

	public CommonFields getCommonFields() {
		return commonFields;
	}

	public void setCommonFields(CommonFields commonFields) {
		this.commonFields = commonFields;
	}

	public void setId(int id) {
		this.id = id;
	}

	/*
	 * @Basic
	 * 
	 * @Column(name = "code") public String getCode() { return code; }
	 * 
	 * public void setCode(String code) { this.code = code; }
	 */

	@Basic
	@Column(name = "Name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "Description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Roles [id=" + id + ", name=" + name + ", description=" + description + ", commonFields=" + commonFields
				+ "]";
	}

}
