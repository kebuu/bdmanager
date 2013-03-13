package com.cta.dao.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtilsBean2;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import com.cta.constants.Operator;
import com.cta.dao.SearchDao;
import com.cta.dto.search.SearchCriteria;
import com.cta.exception.TooManyResultsException;

public class DefaultSearchDao extends AbstractDao implements SearchDao {

	protected ConvertUtilsBean2 converter = new ConvertUtilsBean2();
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Object> searchBy(Class<?> resourceClass, List<SearchCriteria> criterias) {
		Criteria finalCriteria = getSession().createCriteria(resourceClass);

		for (SearchCriteria criteria : criterias) {
			String propertyName = criteria.getPropertyName();
			String propertyValueString = criteria.getPropertyValue();
			Operator operator = criteria.getOperator();

			// On verifie que la propriete existe bien sur la classe de recherche et on recupere son type. Si ce n'est pas le cas, le critere est ignore
			String[] path = propertyName.split("[.]");
			Class<?> targetPropertyClass = getTargetPropertyClass(resourceClass, path);
			
			if (targetPropertyClass != null) {

				Criteria currentCriteria = finalCriteria;
				
				for (int i=0; i < path.length; i++) {
					String pathElement = path[i];
					
					// Seul le dernier element du path est reellement une propriete
					// les precedents sont des chemins au travers des associations
					if(i != path.length - 1) {
						// Ajout d'un criteria sur l'association definie par le path
						currentCriteria = currentCriteria.createCriteria(pathElement);
					} else {
						// Valorise le criteria definie sur la propriete finale 
						Object propertyValue = convertPropertyValue(propertyValueString, operator, targetPropertyClass);
						addCriterion(propertyValue, operator, currentCriteria, pathElement);
					}
				}
			}
		}
		
		finalCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return finalCriteria.list();
	}

	@Override
	public Object searchByUnique(Class<?> resourceClass, List<SearchCriteria> criterias) {
		Object result = null;

		List<Object> resultList = searchBy(resourceClass, criterias);

		if (resultList.size() == 1) {
			result = resultList.get(0);
		} else if (resultList.size() > 1) {
			throw new TooManyResultsException();
		}
		return result;
	}
	
	/**
	 * Convertit la valeur de la propriete, qui est au format String, dans le format cible.
	 * Si l'operateur est un operateur de liste (notamment IN ou NOT IN), on convertit la liste de valeur.
	 */
	private Object convertPropertyValue(String propertyValueString, Operator operator, Class<?> targetPropertyClass) {
		Object propertyValue = null;
		
		if(operator.equals(Operator.IN) || operator.equals(Operator.NOT_IN)) {
			List<Object> propertyValues = new ArrayList<>();
			
			String[] propertyValuesString = propertyValueString.split(",");
			for (String onePropertyValuesString : propertyValuesString) {
				propertyValues.add(convertPropertyValue(onePropertyValuesString, targetPropertyClass));
			}
			propertyValue = propertyValues;
		} else {
			propertyValue = convertPropertyValue(propertyValueString, targetPropertyClass);
		}
		
		return propertyValue;
	}

	/**
	 * Convertit la valeur de la propriete, qui est au format String, dans le format cible.
	 */
	private Object convertPropertyValue(String propertyValueString, Class<?> targetPropertyClass) {
		return converter.convert(propertyValueString, targetPropertyClass);
	}

	/**
	 * Ajoute un criterion au criteria courant.
	 * Si la valeur de la propriete est de type String, le criterion est case insensitive 
	 */
	private void addCriterion(Object propertyValue, Operator operator, Criteria currentCriteria, String pathElement) {
		Criterion criterion = null;
		switch (operator) {
		case EQ:
			criterion = Restrictions.eq(pathElement, propertyValue);
			break;
		case GT:
			criterion = Restrictions.gt(pathElement, propertyValue);
			break;
		case GTE:
			criterion = Restrictions.ge(pathElement, propertyValue);
			break;
		case IN:
			criterion = Restrictions.in(pathElement, (Collection<?>)propertyValue);
			break;
		case LIKE:
			criterion = Restrictions.like(pathElement, (String) propertyValue, MatchMode.ANYWHERE);
			break;
		case LT:
			criterion = Restrictions.lt(pathElement, propertyValue);
			break;
		case LTE:
			criterion = Restrictions.le(pathElement, propertyValue);
			break;
		case NOT_IN:
			criterion = Restrictions.not(Restrictions.in(pathElement, (Collection<?>)propertyValue));
			break;
		case NOT_NULL:
			criterion = Restrictions.isNotNull(pathElement);
			break;
		case NULL:
			criterion = Restrictions.isNull(pathElement);
			break;
		}
		
		if(propertyValue instanceof String && criterion instanceof SimpleExpression) {
			criterion = ((SimpleExpression)criterion).ignoreCase();
		}
		
		currentCriteria.add(criterion);
	}

	/**
	 * Recupere le type de la propriete ciblee par le path pour la ressource donnee.
	 */
	private Class<?> getTargetPropertyClass(Class<?> resourceClass, String[] path) {
		int pathLength = path.length;
		
		if(pathLength == 0) {
			return null;
		} else if(pathLength == 1) {
			return getTargetPropertyClass(resourceClass, path[0]);
		} else {
			if(getTargetPropertyClass(resourceClass, path[0]) != null) {
				return getTargetPropertyClass(getNestedResourceClass(resourceClass, path[0]), ArrayUtils.subarray(path, 1, pathLength));
			} else {
				return null;
			}
		}
	}
	
	private Class<?> getNestedResourceClass(Class<?> resourceClass, String propertyName) {
		Class<?> nestedResourceClass = null;
		
		try {
			
			Field declaredField = resourceClass.getDeclaredField(propertyName);
			Type genericType = declaredField.getGenericType();
			
			if(genericType instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) genericType;
				Class<?> rawType = (Class<?>) parameterizedType.getRawType();
				
				if(Collection.class.isAssignableFrom(rawType)) {
					Type[] types = parameterizedType.getActualTypeArguments();
					nestedResourceClass = (Class<?>) types[0];
				} else {
					nestedResourceClass = rawType;
				}
			} else {
				nestedResourceClass = (Class<?>) genericType;
			}
		} catch (NoSuchFieldException | SecurityException e) {
			// Nothing to do
		}
		
		return nestedResourceClass;
	}

	/**
	 * Recupere le type de la propriete ciblee par le nom indique pour la ressource donnee.
	 */
	private Class<?> getTargetPropertyClass(Class<?> resourceClass, String propertyName) {
		Class<?> result = null;
		try {
			result = resourceClass.getDeclaredField(propertyName).getType();
		} catch (NoSuchFieldException | SecurityException e) {
			// nothing to do
		}
		return result;
	}
}
