package com.cykj.ssh.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "menuBtn", catalog = "ceshi")
public class MenuBtn implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "menu_btn_id", unique = true, nullable = false)
	private Integer menuBtnId;

	@Column(name = "menu_id")
	private Integer menuId;

	@Column(name = "btn_id")
	private Integer btnId;

	public Integer getMenuBtnId() {
		return menuBtnId;
	}

	public void setMenuBtnId(Integer menuBtnId) {
		this.menuBtnId = menuBtnId;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public Integer getBtnId() {
		return btnId;
	}

	public void setBtnId(Integer btnId) {
		this.btnId = btnId;
	}

}
