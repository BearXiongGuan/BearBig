package com.cykj.ssh.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cykj.ssh.dao.MenuDao;

@Repository(value="menuDao")
public class MenuDaoImpl<Menu> extends BaseDaoImpl<Menu> implements MenuDao<Menu>{

	@Override
	public List<Menu> findOperatorMenus(int operatorId) {
		String hql ="select distinct t3 from OperatorRole t1,RoleMenu t2,Menu t3 where t1.roleId = t2.roleId and t2.menuId = t3.menuId and t3.status=0 and t1.opId = '"+operatorId+"'";
		List<Menu> menuList = this.findListByHql(hql);
		return menuList;
	}

	@Override
	public List<Menu> findParentMenusCombobox() {
		String hql ="select m from Menu m where m.pMenuNo is null and m.isLeaf=0 and m.status=0";
		List<Menu> menuList = this.findListByHql(hql);
		return menuList;
	}
	@Override
	public List<Menu> findChildrenMenusByPno(String pno){
		String hql ="select m from Menu m where m.isLeaf=1 and m.status=0 and m.pMenuNo='"+pno+"'";
		List<Menu> menuList = this.findListByHql(hql);
		return menuList;
	}
}
