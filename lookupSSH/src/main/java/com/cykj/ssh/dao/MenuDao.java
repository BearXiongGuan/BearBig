package com.cykj.ssh.dao;

import java.util.List;

public interface MenuDao<Menu>{
	
	/**
	 * 查询操作员拥有的菜单
	 * @param operatorId
	 * @return
	 */
	public List<Menu> findOperatorMenus(int operatorId);
	/**
	 * 查询所有父菜单
	 * @return
	 */
	List<Menu> findParentMenusCombobox(); 
	/**
	 * 根据父菜单编码查询所有子菜单
	 * @param pno
	 * @return
	 */
	List<Menu> findChildrenMenusByPno(String pno);
}
