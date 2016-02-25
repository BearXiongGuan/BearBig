package com.cykj.ssh.dao;

import java.util.List;

public interface MenuDao<Menu>{
	
	/**
	 * ��ѯ����Աӵ�еĲ˵�
	 * @param operatorId
	 * @return
	 */
	public List<Menu> findOperatorMenus(int operatorId);
	/**
	 * ��ѯ���и��˵�
	 * @return
	 */
	List<Menu> findParentMenusCombobox(); 
	/**
	 * ���ݸ��˵������ѯ�����Ӳ˵�
	 * @param pno
	 * @return
	 */
	List<Menu> findChildrenMenusByPno(String pno);
}
