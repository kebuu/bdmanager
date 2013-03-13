package com.cta.service.impl;

import java.util.List;

import lombok.Setter;

import com.cta.dao.CrudDao;
import com.cta.service.CrudService;
import com.cta.service.ModelService;

@Setter
public class DefaultCrudService implements CrudService {

	protected CrudDao crudDao;
	protected ModelService modelService;
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Object> list(String resourceName) {
		Class<?> className = modelService.getQualifiedResourceClassName(resourceName);
		return (List<Object>) crudDao.list(className.getSimpleName());
	}
	
	@Override
	public Object get(String resourceName, Long id) {
		Class<?> rsourceClazz = modelService.getQualifiedResourceClassName(resourceName);
		return crudDao.get(rsourceClazz, id);
	}

	@Override
	public Long create(String resourceName, String jsonData) {
		Object resource = modelService.getResource(resourceName, jsonData);
		return crudDao.create(resource);
	}

	@Override
	public boolean update(String resourceName, String jsonData) {
		Object resource = modelService.getResource(resourceName, jsonData);
		return crudDao.update(resource);
	}

	@Override
	public boolean delete(String resourceName, Long resourceId) {
		Object resource = modelService.getResource(resourceName, "{\"id\":" + resourceId + "}");
		return crudDao.delete(resource);
	}
}
