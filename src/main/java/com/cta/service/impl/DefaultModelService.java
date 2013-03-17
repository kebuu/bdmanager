package com.cta.service.impl;

import java.io.IOException;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.util.ClassUtils;

import com.cta.exception.AppException;
import com.cta.service.ModelService;
import com.cta.utils.MyExceptionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DefaultModelService implements ModelService {

	protected ObjectMapper jsonObjectMapper = new ObjectMapper();

	public Object getResource(String resourceName, String jsonData) {
		return getResourceInstance(jsonData, getQualifiedResourceClassName(resourceName));
	}
	
	public Class<?> getQualifiedResourceClassName(String resourceName) {
		ClassPathScanningCandidateComponentProvider classPathScanner = new ClassPathScanningCandidateComponentProvider(false);
		classPathScanner.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile("(?i).*[.]" + resourceName + "$")));
		Set<BeanDefinition> findCandidateComponents = classPathScanner.findCandidateComponents("com.cta.model");
		
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
	
	public Object getResourceInstance(String jsonData, Class<?> clazz) {
		try {
			return jsonObjectMapper.readValue(jsonData, clazz);
		} catch (IOException e) {
			throw new AppException("resource.population.error", e, clazz.getCanonicalName(), jsonData);
		} 
	}

}
