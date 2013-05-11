package com.cta.search.dao;

import io.searchbox.core.Bulk;

import java.util.List;

import com.cta.model.Bd;
import com.cta.model.Serie;

public interface SearchEngineDao {

	void index(Serie serie);
	
	void index(Bd bd);
	
	void configureMapping();
	
	<T> List<Class<T>> search(String query, Class<T> clazz);

	void indexBulk(Serie serie, Bulk bulk);
	
	void indexBulk(Bd bd, Bulk bulk);

	void executeBulk(Bulk bulk);
	
	void clearIndexType(String indexName, String typeName);
}
