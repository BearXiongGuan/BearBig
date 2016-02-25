package com.cykj.ssh.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cykj.ssh.entity.Menu;
import com.cykj.ssh.service.BaseService;
import com.cykj.ssh.service.MenuService;
import com.cykj.ssh.util.Combobox;
import com.cykj.ssh.util.JSONUtils;
import com.cykj.ssh.util.PageObject;
import com.opensymphony.xwork2.ActionContext;

@ParentPackage("struts-default")
@Namespace("/menu")
@Results({ @Result(name = "error", location = "/html/error.jsp") })
public class MenuController extends BaseController {

	private static final long serialVersionUID = 1L;

	@Resource(name = "baseService")
	BaseService<Menu> baseService;
	
	@Resource(name = "menuService")
	MenuService<Menu> menuService;
	private Integer page;
	private Integer rows;
	private Integer menuId;
	private String menuNo;
	private String menuName;
	private Integer menuType;
	private String menuUrl;
	private String menuIcon;
	private Integer menuOrder;
	private String pMenuNo;

	@Action(value = "gridMenu")
	public void gridMenu() {
		try {
			PageObject<Menu> pageObject = new PageObject<Menu>();
			pageObject.setPage(page);
			pageObject.setSize(rows);
			String hql = "from Menu m where 1=1";
			String countHql = "select count(*) from Menu m where status=0";
			if (!StringUtils.isEmpty(menuNo)) {
				hql += " and m.menuNo = '" + menuNo + "'";
				countHql += " and m.menuNo = '" + menuNo + "'";
			}
			if (!StringUtils.isEmpty(menuName)) {
				hql += " and m.menuName like '%" + menuName + "%'";
				countHql += " and m.menuName like '%" + menuName + "%'";
			}
			List<Menu> menuList = baseService
					.findPageListByHql(hql, pageObject);
			Long total = baseService.countByHql(countHql);
			this.sendMsg("{\"total\":" + total + ",\"rows\":"
					+ JSONUtils.toJson(menuList) + "}");
		} catch (Exception e) {
			e.printStackTrace();
			this.sendMsg("{\"total\":0,\"rows\":[]}");
		}
	}

	@Action(value = "saveOrUpdateMenu")
	public void saveOrUpdateMenu() {
		try {
			HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
			pMenuNo = request.getParameter("pMenuNo");
			Menu menu = new Menu();
			menu.setCreateTime(new Date());
			menu.setIsLeaf(menuType);
			if(menuType == 1){
				menu.setpMenuNo(pMenuNo);
			}
			menu.setMenuIcon(menuIcon);
			menu.setMenuName(menuName);
			menu.setMenuOrder(menuOrder);
			menu.setMenuUrl(menuUrl);
			menu.setStatus(0);
			menu.setMenuNo(menuNo);
			menu.setMenuId(menuId);
			baseService.saveOrUpdate(menu);
			this.sendMsg("{\"success\":true,\"msg\":\"操作成功\"}");
		} catch (Exception e) {
			e.printStackTrace();
			this.sendMsg("{\"success\":false,\"msg\":\"操作失败\"}");
		}
	}

	@Action(value = "loadParentMenusCombobox")
	public void loadParentMenusCombobox(){
		try {
			List<Menu> menuList = menuService.findParentMenusCombobox();
			List<Combobox> comboList = new ArrayList<Combobox>();
			for(Menu m:menuList){
				Combobox com = new Combobox();
				com.setId(m.getMenuNo());
				com.setText(m.getMenuName());
				comboList.add(com);
			}
			this.sendMsg(JSONUtils.toJson(comboList));
		} catch (Exception e) {
			this.sendMsg("[]");
			e.printStackTrace();
		}
	}
	
	@Action(value = "deleteMenu")
	public void deleteMenu(){
		//如果是子菜单则直接删除，如果是父菜单则判断是否有子菜单，有则不允许删除，无则删除
		List<Menu> menuList = null;
		boolean flag = true;
		String msg = "";
		try {
			if(null != pMenuNo){
				menuList = menuService.findChildrenMenusByPno(pMenuNo);
				if(null == menuList){
					baseService.delete(baseService.findByKey(Menu.class, menuId));
					msg = "删除成功";
				}else{
					msg = "菜单下面存在子菜单，请先删除子菜单。";
					flag=false;
				}
			}else{
				baseService.delete(baseService.findByKey(Menu.class, menuId));
				msg = "删除成功";
			}
			this.sendMsg("{\"success\":"+flag+",\"msg\":\""+msg+"\"}");
		} catch (Exception e) {
			msg = "删除失败";
			flag=false;
			this.sendMsg("{\"success\":"+flag+",\"msg\":\""+msg+"\"}");
			e.printStackTrace();
		}
	}
	
	public BaseService<Menu> getBaseService() {
		return baseService;
	}

	public void setBaseService(BaseService<Menu> baseService) {
		this.baseService = baseService;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
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

	public Integer getMenuType() {
		return menuType;
	}

	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
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

	public String getpMenuNo() {
		return pMenuNo;
	}

	public void setpMenuNo(String pMenuNo) {
		this.pMenuNo = pMenuNo;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

}
