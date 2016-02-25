package com.cykj.ssh.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cykj.ssh.entity.Role;
import com.cykj.ssh.service.BaseService;
import com.cykj.ssh.util.JSONUtils;
import com.cykj.ssh.util.PageObject;

@ParentPackage("struts-default")
@Namespace("/role")
@Results({ @Result(name = "error", location = "/html/error.jsp") })
public class RoleController extends BaseController {
	private static final long serialVersionUID = 1L;

	@Resource(name = "baseService")
	BaseService<Role> baseService;

	private Integer page;
	private Integer rows;
	private String roleName;
	private Integer roleId;
	private Integer operatorId;
	private Role role;

	@Action(value = "gridRole")
	public void gridRole() {
		try {
			PageObject<Role> pageObject = new PageObject<Role>();
			pageObject.setPage(page);
			pageObject.setSize(rows);
			String hql = "from Role r where 1=1";
			String countHql = "select count(*) from Role r where roleStatus=0";
			if (!StringUtils.isEmpty(roleName)) {
				hql += " and r.roleName like '%" + roleName + "%'";
				countHql += " and r.roleName like '%" + roleName + "%'";
			}
			List<Role> roleList = baseService
					.findPageListByHql(hql, pageObject);
			Long total = baseService.countByHql(countHql);
			this.sendMsg("{\"total\":" + total + ",\"rows\":"
					+ JSONUtils.toJson(roleList) + "}");
		} catch (Exception e) {
			e.printStackTrace();
			this.sendMsg("{\"total\":0,\"rows\":[]}");
		}
	}

	@Action(value = "saveOrUpdateRole")
	public void saveOrUpdateRole() {
		try {
			role.setRoleStatus(0);
			baseService.saveOrUpdate(role);
			this.sendMsg("{\"success\":true,\"msg\":\"操作成功\"}");
		} catch (Exception e) {
			e.printStackTrace();
			this.sendMsg("{\"success\":false,\"msg\":\"操作失败\"}");
		}
	}

	@Action(value = "deleteRole")
	public void deleteRole() {
		// 如果是角色配置了相应菜单权限则不允许删除
		Long count = 0l;
		boolean flag = true;
		String msg = "";
		try {
			if (null != roleId) {
				count = baseService
						.countByHql("from RoleMenu rm where rm.roleId='"
								+ roleId + "'");
				if (null == count || count.longValue() == 0) {
					baseService.delete(baseService.findByKey(Role.class,
							roleId));
					msg = "删除成功";
				} else {
					msg = "角色已绑定权限，无法删除。";
					flag = false;
				}
			} else {
				baseService.delete(baseService.findByKey(Role.class,
						roleId));
				msg = "删除成功";
			}
			this.sendMsg("{\"success\":" + flag + ",\"msg\":\"" + msg + "\"}");
		} catch (Exception e) {
			msg = "删除失败";
			flag = false;
			this.sendMsg("{\"success\":" + flag + ",\"msg\":\"" + msg + "\"}");
			e.printStackTrace();
		}
	}
	@Action(value = "findOperatorhasRoles")
	public void findOperatorhasRoles(){
		try {
			List<Role> operatorHasRoles = baseService.findListByHql("select r from OperatorRole opr,Role r where opr.roleId = r.roleId and opr.opId='"+operatorId+"'");
			this.sendMsg(JSONUtils.toJson(operatorHasRoles));
		} catch (Exception e) {
			this.sendMsg("[]");
			e.printStackTrace();
		}
	}
	@Action(value = "findOperatorNotHasRoles")
	public void findOperatorNotHasRoles(){
		try {
			List<Role> operatorNotHasRoles = baseService.findListByHql("select r from Role r where r.roleId not in(select opr.roleId from OperatorRole opr where opr.opId = "+operatorId+")");
			this.sendMsg(JSONUtils.toJson(operatorNotHasRoles));
		} catch (Exception e) {
			this.sendMsg("[]");
			e.printStackTrace();
		}
	}
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

}
