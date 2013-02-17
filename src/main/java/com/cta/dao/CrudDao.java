package com.cta.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CrudDao {
	
	/**
	 * Sauvegarde en base une ressource.
	 * @param resource La ressource a sauvegarder
	 * @return L'id de la ressource (interessant surtout si elle a ete generee)
	 */
	Long create(Object resource);
	
	/**
	 * Met a jour une ressource. Si la ressource.
	 * @param resource La ressource a mettre a jour
	 * @return true si la mise a jour s'est deroulee correctement, false notamment si la ressoure n'a pas ete trouvee
	 */
	boolean update(Object resource);
	
	/**
	 * Supprime une ressource.
	 * @param resource La ressource a supprimer (seule l'attribut id a besoin d'etre renseigne)
	 * @return true si la suppression s'est deroulee correctement, false si la ressoure n'a pas ete trouvee
	 */
	boolean delete(Object resource);
	
	/**
	 * Recupere la iste de toutes les ressources du type donne
	 * @param resourceClassName Le type de ressource demande
	 * @return La liste des ressources trouvees
	 */
	List<? extends Object> list(String resourceClassName);
	
	/**
	 * Recupere une ressource par son type et son id
	 * @param resourceClass Le type de la ressource recherchee
	 * @param id L'id de la ressource recherchee
	 * @return La ressource demandee, ou nulle si cette derniere n'a pas ete trouvee
	 */
	Object get(Class<?> resourceClass, Long id);
}
