package com.cta.dao;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.cta.dto.crud.CrudResult;

@Transactional
public interface CrudDao {
	
	/**
	 * Sauvegarde en base une ressource.
	 * @param resource La ressource a sauvegarder
	 * @return L'id de la ressource (interessant surtout si elle a ete generee)
	 */
	CrudResult create(Object resource);
	
	/**
	 * Met a jour une ressource. Si la ressource existe.
	 * @param resource La ressource a mettre a jour
	 * @return true si la mise a jour s'est deroulee correctement, false notamment si la ressoure n'a pas ete trouvee
	 */
	CrudResult update(Object resource);
	
	/**
	 * Supprime une ressource.
	 * @param resource La ressource a supprimer (seule l'attribut id a besoin d'etre renseigne)
	 * @return true si la suppression s'est deroulee correctement, false si la ressoure n'a pas ete trouvee
	 */
	CrudResult delete(Object resource);
	
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

	/**
	 * Recupere la liste de toutes les ressources du type donne en ne gardant que les propriete qui sont dans la table principale.
	 * Le retour est une liste de Map<String, Object> avec en clé le nom de la propriété.
	 * @param resourceClassName Le type de ressource demandee
	 * @param className La classe du type de ressource demandee
	 * @return La liste des ressources trouvees
	 */
	List<Map<String, ? extends Object>> listShort(String resourceClassName, Class<?> className);
}
