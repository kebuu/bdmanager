package com.cta.service.impl;

import java.util.List;
import java.util.Map;

import lombok.Setter;

import com.cta.dao.CrudDao;
import com.cta.dto.crud.CrudResult;
import com.cta.dto.crud.validation.ValidationResult;
import com.cta.exception.AppException;
import com.cta.exception.ValidationServiceException;
import com.cta.model.Model;
import com.cta.service.CrudService;
import com.cta.service.ModelService;
import com.cta.service.ValidationService;

@Setter
public class DefaultCrudService implements CrudService {

	protected CrudDao crudDao;
	protected ModelService modelService;
	protected ValidationService validationService;
	
	@Override
	public List<? extends Object> list(String resourceName) {
		Class<?> className = modelService.getQualifiedResourceClassName(resourceName);
		return (List<? extends Object>) crudDao.list(className.getSimpleName());
	}
	
	@Override
	public List<Map<String, ? extends Object>> listShort(String resourceName) {
		Class<?> className = modelService.getQualifiedResourceClassName(resourceName);
		return (List<Map<String, ? extends Object>>) crudDao.listShort(className.getSimpleName(), className);
	}
	
	@Override
	public Object get(String resourceName, Long id) {
		Class<?> resourceClazz = modelService.getQualifiedResourceClassName(resourceName);
		return crudDao.get(resourceClazz, id);
	}

	@Override
	public CrudResult create(String resourceName, String jsonData) {
		Object resource = modelService.getResource(resourceName, jsonData);
		validate(resource);
		return crudDao.create(resource);
	}

	@Override
	public CrudResult update(String resourceName, Long id, String jsonData) {
		Model resource = (Model) modelService.getResource(resourceName, jsonData);
		
		if(id != null && resource.getId() != null && !id.equals(resource.getId())) {
			throw new AppException("crud.update.id.mismatch");
		}
		
		validate(resource);
		return crudDao.update(resource);
	}

	@Override
	public CrudResult delete(String resourceName, Long resourceId) {
		Object resource = modelService.getResource(resourceName, "{\"id\":" + resourceId + "}");
		return crudDao.delete(resource);
	}
	

	private void validate(Object resource) {
		ValidationResult validate = validationService.validate(resource);
		if(!validate.isOk()) {
			throw new ValidationServiceException(validate);
		}
	}
}
