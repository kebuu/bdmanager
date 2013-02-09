package com.cta.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CrudDao {
	
	Serializable create(Object resource);
	int update(Object resource);
	boolean delete(Object resource);
	List<? extends Object> list(String resourceName);
}
