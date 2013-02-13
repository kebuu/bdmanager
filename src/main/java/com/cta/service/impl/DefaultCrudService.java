package com.cta.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import lombok.Setter;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.util.ClassUtils;

import com.cta.dao.CrudDao;
import com.cta.exception.AppException;
import com.cta.service.CrudService;
import com.cta.utils.MyExceptionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@Setter
public class DefaultCrudService implements CrudService {

	protected ObjectMapper jsonObjectMapper = new ObjectMapper();
	
	protected CrudDao crudDao;
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Object> list(String resourceName) {
		Class<?> className = getQualifiedResourceClassName(resourceName);
		return (List<Object>) crudDao.list(className.getSimpleName());
	}

	@Override
	public Long create(String resourceName, String jsonData) {
		Object resource = getResource(resourceName, jsonData);
		return crudDao.create(resource);
	}

	@Override
	public boolean update(String resourceName, String jsonData) {
		Object resource = getResource(resourceName, jsonData);
		return crudDao.update(resource);
	}

	@Override
	public boolean delete(String resourceName, Long resourceId) {
		Object resource = getResource(resourceName, "{\"id\":" + resourceId + "}");
		return crudDao.delete(resource);
	}
	
	protected Object getResource(String resourceName, String jsonData) {
		return getResourceInstance(jsonData, getQualifiedResourceClassName(resourceName));
	}

	protected Class<?> getQualifiedResourceClassName(String resourceName) {
		ClassPathScanningCandidateComponentProvider tmp = new ClassPathScanningCandidateComponentProvider(false);
		tmp.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile("(?i).*[.]" + resourceName + "$")));
		Set<BeanDefinition> findCandidateComponents = tmp.findCandidateComponents("com.cta.model");
		
		if(findCandidateComponents.isEmpty()) {
			throw new AppException("unknown.resource.name", resourceName);
		} else {
			try {
				BeanDefinition firstResourceMatch = findCandidateComponents.iterator().next();
				Class<?> forName = ClassUtils.forName(firstResourceMatch.getBeanClassName(),this.getClass().getClassLoader());
				return forName;
			} catch (ClassNotFoundException | LinkageError  e) {
				throw MyExceptionUtils.unhandle(e);
			} 
		}
	}
	
	protected Object getResourceInstance(String jsonData, Class<?> clazz) {
		try {
			return jsonObjectMapper.readValue(jsonData, clazz);
		} catch (IOException  e) {
			throw new AppException("resource.population.error", clazz.getCanonicalName(), jsonData);
		} 
	}
}
