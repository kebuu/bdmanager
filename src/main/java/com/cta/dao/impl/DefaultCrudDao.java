package com.cta.dao.impl;

import java.util.List;

import org.hibernate.StaleStateException;

import com.cta.dao.CrudDao;

public class DefaultCrudDao extends AbstractDao implements CrudDao {

	@Override
	public Long create(Object resource) {
		return (Long) getSession().save(resource);
	}

	@Override
	public boolean update(Object resource) {
		boolean didUpdateWork = true;
		
		try {
			getSession().update(resource);
			getSession().flush();
		} catch (StaleStateException e) {
			didUpdateWork = false;
		}
		return didUpdateWork;
	}

	@Override
	public boolean delete(Object resource) {
		boolean didUpdateWork = true;
		
		try {
			getSession().delete(resource);
			getSession().flush();
		} catch (StaleStateException e) {
			didUpdateWork = false;
		}
		
		return didUpdateWork;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<? extends Object> list(String resourceName) {
		return getSession().createQuery("from " + resourceName).list();
	}
}
