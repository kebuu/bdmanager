package com.cta.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.cta.dto.crud.CrudResult;

@Transactional
public interface CrudService {

	/**
	 * Cree et sauvegarde une ressource a partir du type de la ressource
	 * et de ses donnees au format JSON
	 * @param resourceName Le type de ressource a creer
	 * @param jsonData Les donnees de la ressource au format JSON
	 * @return L'id de la ressource creee
	 */
	CrudResult create(String resourceName, String jsonData);
	
	/**
	 * Met a jour une ressource a partir du type de la ressource
	 * et de ses donnees au format JSON
	 * @param resourceName Le type de ressource a creer
	 * @param id identifiant de la ressource a mettre a jour. 
	 * S'il existe un id dans le jsonData, on s'assure qu'il s'agit du meme id.
	 * @param jsonData Les donnees de la ressource au format JSON
	 * @return true si la mise a jour s'est deroulee correctement, false notamment si la ressoure n'a pas ete trouvee
	 */
	CrudResult update(String resourceName, Long id, String jsonData);
	
	/**
	 * Supprime une ressource a partir de son type et de son id
	 * @param resourceName Le type de ressource a creer
	 * @param resourceId L'id de la ressource a supprimer
	 * @return true si la suppression s'est deroulee correctement, false si la ressoure n'a pas ete trouvee
	 */
	CrudResult delete(String resourceName, Long resourceId);
	
	/**
	 * Recupere la liste de toutes les ressources du type donne
	 * @param resourceClassName Le type de ressource demande
	 * @return La liste des ressources trouvees
	 */
	List<? extends Object> list(String resourceName);
	
	/**
	 * Recupere une ressource par son type et son id
	 * @param resourceName Le type de la ressource recherchee
	 * @param id L'id de la ressource recherchee
	 * @return La ressource demandee, ou nulle si cette derniere n'a pas ete trouvee
	 */
	Object get(String resourceName, Long id);
	
	/**
	 * Recupere la liste de toutes les ressources du type donne en ne gardant que les propriete qui sont dans la table principale.
	 * Le retour est une liste de Map<String, Object> avec en clé le nom de la propriété.
	 * @param resourceClassName Le type de ressource demande
	 * @return La liste des ressources trouvees
	 */
	List<Map<String, ? extends Object>> listShort(String resourceName);
}
