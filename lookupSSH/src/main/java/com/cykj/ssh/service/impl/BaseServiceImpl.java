package com.cykj.ssh.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cykj.ssh.dao.BaseDao;
import com.cykj.ssh.service.BaseService;
import com.cykj.ssh.util.PageObject;


@Service(value="baseService")
@Transactional
public class BaseServiceImpl<T> implements BaseService<T>{

	@Resource(name="baseDao")
	BaseDao<T> baseDao;
	
	@Override
	public T findByKey(Class clazz,Serializable key) {
		return baseDao.findByKey(clazz,key);
	}

	@Override
	public T findSingleResultBySql(String sql) {
		return baseDao.findSingleResultBySql(sql);
	}

	@Override
	public T findSingleResultByHql(String hql) {
		return baseDao.findSingleResultByHql(hql);
	}

	@Override
	public List<T> findListBySql(String sql) {
		return baseDao.findListBySql(sql);
	}

	@Override
	public List<T> findPageListBySql(String sql, PageObject<T> page) {
		return baseDao.findPageListBySql(sql, page);
	}

	@Override
	public List<T> findListByHql(String hql) {
		return baseDao.findListByHql(hql);
	}

	@Override
	public List<T> findPageListByHql(String hql, PageObject<T> page) {
		return baseDao.findPageListByHql(hql, page);
	}
	@Override
	public Long countByHql(String hql){
		return baseDao.countByHql(hql);
	}
	
	@Override
	public Long countBySql(String sql){
		return baseDao.countBySql(sql);
	}

	@Override
	public boolean save(T t) {
		return baseDao.save(t);
	}

	@Override
	public boolean saveOrUpdate(T t) {
		return baseDao.saveOrUpdate(t);
	}

	@Override
	public boolean update(T t) {
		return baseDao.update(t);
	}
	
	@Override
	public boolean delete(T t){
		return baseDao.delete(t);
	}
	
	public BaseDao<T> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}
	
	
}
