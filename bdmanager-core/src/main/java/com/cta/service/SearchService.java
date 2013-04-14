package com.cta.service;

import java.util.List;

import com.cta.dto.search.SearchCriteria;

public interface SearchService {

	List<Object> getResources(String resourceName, List<SearchCriteria> criterias);
	
	Object getResource(String resourceName, List<SearchCriteria> criterias);
}
