package com.cykj.ssh.util;

import java.util.List;
import java.util.Map;

public class MenuCheckTree {

	private int id;

	private String text;

	private String state = "open";

	private String iconCls;

	private boolean checked = false;

	private List<MenuCheckTree> children;

	private Map<String, Object> attributes;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public List<MenuCheckTree> getChildren() {
		return children;
	}

	public void setChildren(List<MenuCheckTree> children) {
		this.children = children;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
