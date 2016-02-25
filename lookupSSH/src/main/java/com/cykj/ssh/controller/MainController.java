package com.cykj.ssh.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cykj.ssh.entity.Menu;
import com.cykj.ssh.entity.Operator;
import com.cykj.ssh.service.BaseService;
import com.cykj.ssh.service.MenuService;
import com.cykj.ssh.util.JSONUtils;
import com.cykj.ssh.util.MenuTree;
import com.opensymphony.xwork2.ActionContext;

@ParentPackage("struts-default")
@Namespace("/main")
@Results({ @Result(name = "success", location = "/html/main.jsp"),
		@Result(name = "error", location = "/html/error.jsp") })
public class MainController extends BaseController {
	private static final long serialVersionUID = 1L;
	@Resource(name = "baseService")
	BaseService<Operator> baseService;
	@Resource(name = "menuService")
	MenuService<Menu> menuService;

	private String username;
	private String password;

	public String toMain() {
		return "main";
	}

	@Action(value = "login")
	public void login() {
		try {
			if (StringUtils.isEmpty(username)) {
				sendMsg("{\"success\":false,\"msg\":\"用户名不能为空\"}");
				return;
			}
			if (StringUtils.isEmpty(password)) {
				sendMsg("{\"success\":false,\"msg\":\"密码不能为空\"}");
				return;
			}
			String hql = "select count(*) from Operator o where o.username = '"
					+ username + "'";
			Long count = baseService.countByHql(hql);
			if (count == 0) {
				sendMsg("{\"success\":false,\"msg\":\"用户名不存在\"}");
				return;
			} else {
				hql = "from Operator o where o.username = '" + username
						+ "' and o.password = '" + password + "'";
			}
			Operator op = baseService.findSingleResultByHql(hql);
			if (null == op) {
				sendMsg("{\"success\":false,\"msg\":\"密码不正确\"}");
				return;
			} else {
				Map<String, Object> session = ActionContext.getContext()
						.getSession();
				session.put("username", username);
				session.put("operatorId", op.getOperatorId());
				sendMsg("{\"success\":true,\"msg\":\"登录成功\"}");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Action(value = "loadMenuTree")
	public void loadMenuTree() {
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			Integer operatorId = (Integer) session.get("operatorId");
			List<Menu> menuList = menuService.findOperatorMenus(operatorId);
			List<MenuTree> menuTreeList = createMenuTreeList(menuList);
			String jsonTree = JSONUtils.toJson(menuTreeList);
			sendMsg(jsonTree);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<MenuTree> createMenuTreeList(List<Menu> menuList) {
		List<MenuTree> menuTreeList = new ArrayList<MenuTree>();
		for (Menu m : menuList) {
			if (StringUtils.isEmpty(m.getpMenuNo()) && m.getIsLeaf() == 0) {
				MenuTree rootElement = new MenuTree();
				rootElement.setId(m.getMenuId());
				rootElement.setText(m.getMenuName());
				rootElement.setIconCls(m.getMenuIcon());
				Map<String, Object> attributes = new HashMap<String, Object>();
				attributes.put("menuUrl", m.getMenuUrl());
				attributes.put("menuNo", m.getMenuNo() == null ? "" : m
						.getMenuNo().trim());
				attributes.put("menuOrder", m.getMenuOrder());
				attributes.put("isLeaf",m.getIsLeaf());
				rootElement.setAttributes(attributes);
				menuTreeList.add(rootElement);
			} else {
				for (MenuTree tree : menuTreeList) {
					List<MenuTree> children = new ArrayList<MenuTree>();
					for (Menu mn : menuList) {
						if (null != mn.getpMenuNo()
								&& "" != tree.getAttributes().get("menuNo")
								&& mn.getpMenuNo()
										.trim()
										.equals((String) tree.getAttributes()
												.get("menuNo"))) {
							MenuTree child = new MenuTree();
							child.setId(mn.getMenuId());
							child.setText(mn.getMenuName());
							child.setIconCls(mn.getMenuIcon());
							Map<String, Object> attributes = new HashMap<String, Object>();
							attributes.put("menuUrl", mn.getMenuUrl());
							attributes.put("menuNo", mn.getMenuNo());
							attributes.put("menuOrder", mn.getMenuOrder());
							attributes.put("isLeaf",mn.getIsLeaf());
							child.setAttributes(attributes);
							children.add(child);
						}
					}
					tree.setChildren(children);
				}
			}
		}
		return menuTreeList;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
