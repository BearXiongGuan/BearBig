package com.cykj.ssh.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cykj.ssh.dao.BaseDao;
import com.cykj.ssh.util.PageObject;

@Repository(value="baseDao")
public class BaseDaoImpl<T> implements BaseDao<T> {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	private Class<T> enityClass;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public T findByKey(Class clazz, Serializable key) {
		Session session = sessionFactory.getCurrentSession();
		return (T) session.get(clazz, key);
	}

	@Override
	public T findSingleResultBySql(String sql) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createSQLQuery(sql);
			return (T) query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public T findSingleResultByHql(String hql) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery(hql);
			return (T) query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<T> findListBySql(String sql) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createSQLQuery(sql);
			return (List<T>) query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<T> findPageListBySql(String sql, PageObject<T> page) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createSQLQuery(sql);
			query.setFirstResult((page.getPage() - 1) * page.getSize());
			query.setMaxResults(page.getSize());
			return (List<T>) query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<T> findListByHql(String hql) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery(hql);
			return (List<T>) query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<T> findPageListByHql(String hql, PageObject<T> page) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery(hql);
			query.setFirstResult((page.getPage() - 1) * page.getSize());
			query.setMaxResults(page.getSize());
			return (List<T>) query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean save(T t) {
		boolean flag = true;
		Session session = null;
		// Transaction tx = null;
		try {
			session = sessionFactory.getCurrentSession();
			// tx = session.beginTransaction();
			session.save(t);
			// tx.commit();
		} catch (DataAccessException e) {
			flag = false;
			// tx.rollback();
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean saveOrUpdate(T t) {
		boolean flag = true;
		Session session = null;
		// Transaction tx = null;
		try {
			session = sessionFactory.getCurrentSession();
			// tx = session.beginTransaction();
			session.saveOrUpdate(t);
			// tx.commit();
		} catch (DataAccessException e) {
			flag = false;
			// tx.rollback();
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean update(T t) {
		boolean flag = true;
		Session session = null;
		// Transaction tx = null;
		try {
			session = sessionFactory.getCurrentSession();
			// tx = session.beginTransaction();
			session.update(t);
			// tx.commit();
		} catch (DataAccessException e) {
			flag = false;
			// tx.rollback();
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Long countByHql(String hql) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery(hql);
			return (Long)query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
			return 0l;
		}
	}

	@Override
	public Long countBySql(String sql) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createSQLQuery(sql);
			return (Long)query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
			return 0l;
		}
	}
	
	@Override
	public boolean delete(T t){
		boolean flag = true;
		Session session = sessionFactory.getCurrentSession();
		try {
			session.delete(t);
		} catch (HibernateException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
}
