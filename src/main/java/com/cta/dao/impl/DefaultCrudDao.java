package com.cta.dao.impl;

import java.io.Serializable;
import java.util.List;


import com.cta.dao.CrudDao;

public class DefaultCrudDao extends AbstractDao implements CrudDao {

	@Override
	public Serializable create(Object resource) {
		return getSession().save(resource);
	}

	@Override
	public int update(Object resource) {
		getSession().update(resource);
		return 0;
	}

	@Override
	public boolean delete(Object resource) {
		getSession().delete(resource);
		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<? extends Object> list(String resourceName) {
		return getSession().createQuery("from " + resourceName).list();
	}
}
