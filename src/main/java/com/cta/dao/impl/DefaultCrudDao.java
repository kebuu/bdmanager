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
		} catch (StaleStateException e) {
			didUpdateWork = false;
		}
		return didUpdateWork;
	}

	@Override
	public boolean delete(Object resource) {
		boolean didDeleteWork = true;
		
		try {
			getSession().merge(resource);
			getSession().delete(resource);
		} catch (StaleStateException e) {
			didDeleteWork = false;
		}
		
		return didDeleteWork;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<? extends Object> list(String resourceClassName) {
		return getSession().createQuery("from " + resourceClassName).list();
	}
	
	@Override
	public Object get(Class<?> resourceClass, Long id) {
		return getSession().get(resourceClass, id);
	}
}
