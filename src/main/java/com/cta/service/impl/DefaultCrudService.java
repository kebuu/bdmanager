package com.cta.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Setter;

import com.cta.dao.CrudDao;
import com.cta.service.CrudService;

@Setter
public class DefaultCrudService implements CrudService {

	protected CrudDao crudDao;

	@Override
	public Serializable create(String resourceName, Map<String, String> data) {
		Object resource = getResource(resourceName, data);
		return crudDao.create(resource);
	}

	@Override
	public int update(String resourceName, Map<String, String> data) {
		Object resource = getResource(resourceName, data);
		return crudDao.update(resource);
	}

	@Override
	public boolean delete(String resourceName, Serializable resourceId) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("id", resourceId.toString());
		Object resource = getResource(resourceName, data);
		return crudDao.delete(resource);
	}

	@Override
	public List<? extends Object> list(String resourceName) {
		return crudDao.list(resourceName);
	}
	
	protected Object getResource(String resourceName, Map<String, String> data) {
		return populateResource(getResourceFromResourceName(resourceName), data);
	}
	
	protected Object getResourceFromResourceName(String resourceName) {
		return null;
	}
	
	protected Object populateResource(Object resource, Map<String, String> data) {
		return null;
	}
}
