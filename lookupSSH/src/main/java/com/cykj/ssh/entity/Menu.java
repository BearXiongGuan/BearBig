package com.cykj.ssh.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "menu", catalog = "ceshi")
public class Menu implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "menu_id", unique = true, nullable = false)
	private Integer menuId;

	@Column(name = "menu_no", length = 50)
	private String menuNo;

	@Column(name = "menu_name", length = 50)
	private String menuName;

	@Column(name = "parent_menu_no", length = 50)
	private String pMenuNo;

	@Column(name = "menu_url", length = 100)
	private String menuUrl;

	@Column(name = "menu_icon", length = 50)
	private String menuIcon;

	@Column(name = "menu_order")
	private Integer menuOrder;

	@Column(name = "isLeaf")
	private Integer isLeaf;

	@Column(name = "status")
	private Integer status;

	@Column(name = "create_time")
	private Date createTime;

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getMenuNo() {
		return menuNo;
	}

	public void setMenuNo(String menuNo) {
		this.menuNo = menuNo;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getpMenuNo() {
		return pMenuNo;
	}

	public void setpMenuNo(String pMenuNo) {
		this.pMenuNo = pMenuNo;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public Integer getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}

	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
