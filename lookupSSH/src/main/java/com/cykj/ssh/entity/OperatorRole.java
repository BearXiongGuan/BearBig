package com.cykj.ssh.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "operator_role", catalog = "ceshi")
public class OperatorRole implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "operator_role_id", unique = true, nullable = false)
	private Integer opRoleId;

	@Column(name = "operator_id")
	private Integer opId;

	@Column(name = "role_id")
	private Integer roleId;

	public Integer getOpRoleId() {
		return opRoleId;
	}

	public void setOpRoleId(Integer opRoleId) {
		this.opRoleId = opRoleId;
	}

	public Integer getOpId() {
		return opId;
	}

	public void setOpId(Integer opId) {
		this.opId = opId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
