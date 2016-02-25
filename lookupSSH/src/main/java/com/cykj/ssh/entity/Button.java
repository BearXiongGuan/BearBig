package com.cykj.ssh.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "button", catalog = "ceshi")
public class Button implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "btn_id", unique = true, nullable = false)
	private Integer btnId;

	@Column(name = "btn_no", length = 50)
	private String btnNo;

	@Column(name = "btn_name", length = 50)
	private String btnName;

	@Column(name = "btn_desc", length = 100)
	private String btnDesc;
	@Column(name = "btn_url", length = 100)
	private String btnUrl;
	@Column(name = "btn_status")
	private Integer btnStatus;

	public Integer getBtnId() {
		return btnId;
	}

	public void setBtnId(Integer btnId) {
		this.btnId = btnId;
	}

	public String getBtnNo() {
		return btnNo;
	}

	public void setBtnNo(String btnNo) {
		this.btnNo = btnNo;
	}

	public String getBtnName() {
		return btnName;
	}

	public void setBtnName(String btnName) {
		this.btnName = btnName;
	}

	public String getBtnDesc() {
		return btnDesc;
	}

	public void setBtnDesc(String btnDesc) {
		this.btnDesc = btnDesc;
	}

	public String getBtnUrl() {
		return btnUrl;
	}

	public void setBtnUrl(String btnUrl) {
		this.btnUrl = btnUrl;
	}

	public Integer getBtnStatus() {
		return btnStatus;
	}

	public void setBtnStatus(Integer btnStatus) {
		this.btnStatus = btnStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
