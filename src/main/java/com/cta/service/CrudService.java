package com.cta.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CrudService {

	Long create(String resourceName, String jsonData);
	boolean update(String resourceName, String jsonData);
	boolean delete(String resourceName, Long resourceId);
	List<Object> list(String resourceName);
}
