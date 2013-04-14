package com.cta.service;

public interface ModelService {

	/**
	 * Recupere la classe correspondant au nom donne a partir du package des models
	 * @param resourceName Le nom de la ressource cherchee
	 * @return La classe associee au nom de la ressource cherchee
	 */
	Class<?> getQualifiedResourceClassName(String resourceName);

	/**
	 * Cree une instance du type donne et la peuple avec les donnees fournies au format json
	 * @param jsonData Les donnees au format json
	 * @param clazz Le type de l'instance a creer
	 * @return L'objet cree et peuple
	 */
	Object getResourceInstance(String jsonData, Class<?> clazz);

	/**
	 * Cree une instance du type donne et la peuple avec les donnees fournies au format json
	 * @param jsonData Les donnees au format json
	 * @param resourceName Le type de l'instance a creer
	 * @return L'objet cree et peuple
	 */
	Object getResource(String resourceName, String jsonData);

}