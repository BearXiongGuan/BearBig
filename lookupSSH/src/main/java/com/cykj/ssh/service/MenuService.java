package com.cykj.ssh.service;

import java.util.List;

public interface MenuService<Menu> extends BaseService<Menu>{

	List<Menu> findOperatorMenus(int operatorId);
	
	List<Menu> findParentMenusCombobox(); 
	
	List<Menu> findChildrenMenusByPno(String pno);
}
