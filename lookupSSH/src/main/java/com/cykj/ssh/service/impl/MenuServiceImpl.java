package com.cykj.ssh.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cykj.ssh.dao.MenuDao;
import com.cykj.ssh.service.MenuService;

@Service(value="menuService")
@Transactional
public class MenuServiceImpl<Menu> extends BaseServiceImpl<Menu> implements MenuService<Menu>{
	@Resource(name="menuDao")
	MenuDao<Menu> menuDao;
	@Override
	public List<Menu> findOperatorMenus(int operatorId) {
		return menuDao.findOperatorMenus(operatorId);
	}
	@Override
	public List<Menu> findParentMenusCombobox() {
		return  menuDao.findParentMenusCombobox();
 	}
	@Override
	public List<Menu> findChildrenMenusByPno(String pno){
		return  menuDao.findChildrenMenusByPno(pno);
	}
}
