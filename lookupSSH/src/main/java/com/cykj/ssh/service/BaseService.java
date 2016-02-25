package com.cykj.ssh.service;

import java.io.Serializable;
import java.util.List;

import com.cykj.ssh.util.PageObject;


public interface BaseService<T> {

	public T findByKey(Class clazz,Serializable key);

	public T findSingleResultBySql(String sql);

	public T findSingleResultByHql(String hql);

	public List<T> findListBySql(String sql);

	public List<T> findPageListBySql(String sql, PageObject<T> page);

	public List<T> findListByHql(String hql);

	public List<T> findPageListByHql(String hql, PageObject<T> page);
	
	public Long countByHql(String hql);
	
	public Long countBySql(String sql);

	public boolean save(T t);

	public boolean saveOrUpdate(T t);

	public boolean update(T t);
	
	public boolean delete(T t);
}
