package com.cykj.ssh.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cykj.ssh.entity.OperatorRole;
import com.cykj.ssh.service.BaseService;

@ParentPackage("struts-default")
@Namespace("/operatorRole")
@Results({ @Result(name = "error", location = "/html/error.jsp") })
public class OperatorRoleController extends BaseController{
	
	private static final long serialVersionUID = 1L;
	@Resource(name = "baseService")
	BaseService<OperatorRole> baseService;
	private Integer operatorId;
	private String ids;
	
	@Action(value = "updateOperatorRoles")
	public void updateOperatorRoles(){
		try {
			List<OperatorRole> operatorHasRoles = baseService.findListByHql("select opr from OperatorRole opr,Role r where opr.roleId = r.roleId and opr.opId='"+operatorId+"'");
			//ɾ��ԭ���İ󶨽�ɫ
			for(OperatorRole opr:operatorHasRoles){
				baseService.delete(opr);
			}
			//���µĽ�ɫ
			if(!ids.equals("")){
				String[] idsArray = ids.split(",");
				for(String id:idsArray){
					OperatorRole opr = new OperatorRole();
					opr.setOpId(operatorId);
					opr.setRoleId(Integer.valueOf(id));
					baseService.save(opr);
				}
			}
			this.sendMsg("{\"success\":true,\"msg\":\"�󶨽�ɫ�ɹ�\"}");
		} catch (Exception e) {
			this.sendMsg("{\"success\":false,\"msg\":\"�󶨽�ɫʧ��\"}");
			e.printStackTrace();
		}
	}
	public Integer getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	
}
