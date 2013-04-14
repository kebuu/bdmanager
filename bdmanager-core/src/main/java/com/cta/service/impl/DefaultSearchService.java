package com.cta.service.impl;

import java.util.List;

import lombok.Setter;

import com.cta.dao.SearchDao;
import com.cta.dto.search.SearchCriteria;
import com.cta.service.ModelService;
import com.cta.service.SearchService;

@Setter
public class DefaultSearchService implements SearchService {

	protected ModelService modelService;
	protected SearchDao searchDao;
	
	@Override
	public List<Object> getResources(String resourceName, List<SearchCriteria> criterias) {
		Class<?> resourceClass = modelService.getQualifiedResourceClassName(resourceName);
		return searchDao.searchBy(resourceClass, criterias);
	}

	@Override
	public Object getResource(String resourceName, List<SearchCriteria> criterias) {
		Class<?> resourceClass = modelService.getQualifiedResourceClassName(resourceName);
		return searchDao.searchByUnique(resourceClass, criterias);
	}
}
