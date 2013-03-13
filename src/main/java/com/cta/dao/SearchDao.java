package com.cta.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.cta.dto.search.SearchCriteria;

@Transactional(readOnly=true)
public interface SearchDao {

	/**
	 * Recupere une liste de ressource en filtrant la ressource demandee a l'aide des criteria fournis
	 */
	List<Object> searchBy(Class<?> resourceClass, List<SearchCriteria> criterias);
	
	/**
	 * Recupere un ressource en filtrant la ressource demandee a l'aide des criteria fournis.
	 * Retourne nul si aucun objet n'est trouve.
	 * Lance une exception si plus d'un objet est trouve 
	 */
	Object searchByUnique(Class<?> resourceClass, List<SearchCriteria> criterias);
}
