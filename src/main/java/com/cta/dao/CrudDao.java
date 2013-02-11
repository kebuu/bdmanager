package com.cta.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CrudDao {
	
	Long create(Object resource);
	boolean update(Object resource);
	boolean delete(Object resource);
	List<? extends Object> list(String resourceName);
}
