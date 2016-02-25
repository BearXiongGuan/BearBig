package com.cykj.ssh.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cykj.ssh.entity.Menu;
import com.cykj.ssh.entity.RoleMenu;
import com.cykj.ssh.service.BaseService;
import com.cykj.ssh.service.MenuService;
import com.cykj.ssh.util.JSONUtils;
import com.cykj.ssh.util.MenuCheckTree;

@ParentPackage("struts-default")
@Namespace("/roleMenu")
@Results({ @Result(name = "error", location = "/html/error.jsp") })
public class RoleMenuController extends BaseController {
	private static final long serialVersionUID = 1L;

	@Resource(name = "baseService")
	BaseService<RoleMenu> baseService;

	@Resource(name = "menuService")
	MenuService<Menu> menuService;

	private RoleMenu roleMenu;

	private Integer roleId;

	private String ids;

	@Action(value = "treeRoleMenus")
	public void treeRoleMenus() {
		try {
			List<Menu> menuList = menuService
					.findListByHql("from Menu m where m.status=0");
			List<RoleMenu> roleMenuList = baseService
					.findListByHql("from RoleMenu rm where rm.roleId=" + roleId);
			List<MenuCheckTree> menuTreeList = createMenuTreeList(menuList);
			for (MenuCheckTree mc : menuTreeList) {
				if(null==mc.getChildren()||mc.getChildren().size()==0){
					break;
				}
				for (RoleMenu rm : roleMenuList) {
					for (MenuCheckTree child : mc.getChildren()) {
						if (rm.getMenuId() == child.getId()) {
							child.setChecked(true);
						}
					}
				}
			}

			String jsonTree = JSONUtils.toJson(menuTreeList);
			sendMsg(jsonTree);

		} catch (Exception e) {
			e.printStackTrace();
			this.sendMsg("{\"total\":0,\"rows\":[]}");
		}
	}

	@Action(value = "updateRoleMenus")
	public void updateRoleMenus() {
		try {
			//1.清除role_menu表中roleId对应的menu
			List<RoleMenu> roleMenus = baseService.findListByHql("select rm from RoleMenu rm where rm.roleId='"+roleId+"'") ;
			for(RoleMenu rm:roleMenus){
				baseService.delete(rm);
			}
			String[] idsArray = ids.split(",");
			for(String id:idsArray){
				RoleMenu roleMenu = new RoleMenu();
				roleMenu.setRoleId(roleId);
				roleMenu.setMenuId(Integer.valueOf(id));
				baseService.save(roleMenu);
			}
			this.sendMsg("{\"success\":true,\"msg\":\"操作成功\"}");
		} catch (Exception e) {
			e.printStackTrace();
			this.sendMsg("{\"success\":false,\"msg\":\"操作失败\"}");
		}
	}

	public List<MenuCheckTree> createMenuTreeList(List<Menu> menuList) {
		List<MenuCheckTree> menuTreeList = new ArrayList<MenuCheckTree>();
		for (Menu m : menuList) {
			if (StringUtils.isEmpty(m.getpMenuNo()) && m.getIsLeaf() == 0) {
				MenuCheckTree rootElement = new MenuCheckTree();
				rootElement.setId(m.getMenuId());
				rootElement.setText(m.getMenuName());
				rootElement.setIconCls(m.getMenuIcon());
				rootElement.setChecked(false);
				Map<String, Object> attributes = new HashMap<String, Object>();
				attributes.put("menuUrl", m.getMenuUrl());
				attributes.put("menuNo", m.getMenuNo() == null ? "" : m
						.getMenuNo().trim());
				attributes.put("menuOrder", m.getMenuOrder());
				attributes.put("isLeaf", m.getIsLeaf());
				rootElement.setAttributes(attributes);
				menuTreeList.add(rootElement);
			} else {
				for (MenuCheckTree tree : menuTreeList) {
					List<MenuCheckTree> children = new ArrayList<MenuCheckTree>();
					for (Menu mn : menuList) {
						if (null != mn.getpMenuNo()
								&& "" != tree.getAttributes().get("menuNo")
								&& mn.getpMenuNo()
										.trim()
										.equals((String) tree.getAttributes()
												.get("menuNo"))) {
							MenuCheckTree child = new MenuCheckTree();
							child.setId(mn.getMenuId());
							child.setText(mn.getMenuName());
							child.setIconCls(mn.getMenuIcon());
							child.setChecked(false);
							Map<String, Object> attributes = new HashMap<String, Object>();
							attributes.put("menuUrl", m.getMenuUrl());
							attributes.put("menuNo", mn.getMenuNo());
							attributes.put("menuOrder", mn.getMenuOrder());
							attributes.put("isLeaf", m.getIsLeaf());
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

	public RoleMenu getRoleMenu() {
		return roleMenu;
	}

	public void setRoleMenu(RoleMenu roleMenu) {
		this.roleMenu = roleMenu;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

}
