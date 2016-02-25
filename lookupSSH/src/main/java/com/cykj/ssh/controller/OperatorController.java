package com.cykj.ssh.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cykj.ssh.entity.Operator;
import com.cykj.ssh.service.BaseService;
import com.cykj.ssh.util.JSONUtils;
import com.cykj.ssh.util.PageObject;

@ParentPackage("struts-default")
@Namespace("/operator")
@Results({ @Result(name = "error", location = "/html/error.jsp") })
public class OperatorController extends BaseController {
	private static final long serialVersionUID = 1L;

	@Resource(name = "baseService")
	BaseService<Operator> baseService;

	private Integer page;
	private Integer rows;
	private String operatorName;
	private String username;
	private Integer operatorId;
	private Operator operator;

	@Action(value = "gridOperator")
	public void gridOperator() {
		try {
			PageObject<Operator> pageObject = new PageObject<Operator>();
			pageObject.setPage(page);
			pageObject.setSize(rows);
			String hql = "from Operator r where 1=1";
			String countHql = "select count(*) from Operator r where status=0";
			if (!StringUtils.isEmpty(operatorName)) {
				hql += " and r.operatorName like '%" + operatorName + "%'";
				countHql += " and r.operatorName like '%" + operatorName + "%'";
			}
			if (!StringUtils.isEmpty(username)) {
				hql += " and r.username  = '" + username + "'";
				countHql += " and r.username  = '" + username + "'";
			}
			List<Operator> opList = baseService.findPageListByHql(hql,
					pageObject);
			Long total = baseService.countByHql(countHql);
			this.sendMsg("{\"total\":" + total + ",\"rows\":"
					+ JSONUtils.toJson(opList) + "}");
		} catch (Exception e) {
			e.printStackTrace();
			this.sendMsg("{\"total\":0,\"rows\":[]}");
		}
	}

	@Action(value = "saveOrUpdateOperator")
	public void saveOrUpdateOperator() {
		try {
			if(null == operator.getOperatorId()){
				operator.setCreateTime(new Date());
			}
			operator.setStatus(0);
			baseService.saveOrUpdate(operator);
			this.sendMsg("{\"success\":true,\"msg\":\"操作成功\"}");
		} catch (Exception e) {
			e.printStackTrace();
			this.sendMsg("{\"success\":false,\"msg\":\"操作失败\"}");
		}
	}

	@Action(value = "deleteOperator")
	public void deleteOperator() {
		// 如果是角色配置了相应菜单权限则不允许删除
		Long count = 0l;
		boolean flag = true;
		String msg = "";
		try {
			if (null != operatorId) {
				count = baseService
						.countByHql("from OperatorRole or where or.opId='"
								+ operatorId + "'");
				if (null == count || count.longValue() == 0) {
					baseService.delete(baseService.findByKey(Operator.class,
							operatorId));
					msg = "删除成功";
				} else {
					msg = "角色已绑定权限，无法删除。";
					flag = false;
				}
			} else {
				baseService.delete(baseService.findByKey(Operator.class,
						operatorId));
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

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

}
