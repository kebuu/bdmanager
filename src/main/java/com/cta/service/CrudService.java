package com.cta.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface CrudService {

	Serializable create(String resourceName, Map<String, String> data);
	int update(String resourceName, Map<String, String> data);
	boolean delete(String resourceName, Serializable resourceId);
	List<? extends Object> list(String resourceName);
}
