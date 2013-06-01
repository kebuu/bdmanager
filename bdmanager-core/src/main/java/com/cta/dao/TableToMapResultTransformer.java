package com.cta.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.transform.ResultTransformer;

@Setter
@AllArgsConstructor
@NoArgsConstructor
/**
 * This ResultTransformer will only map properties present in the result set of the query.
 * Associated collections will not be retrieved 
 */
public class TableToMapResultTransformer implements ResultTransformer {

	private static final long serialVersionUID = 1L;
	
	protected SessionFactory sessionFactory;
	protected Class<?> entityClass;
	
	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Map<String, Object> map = new HashMap<>();
		
		// There is no "instanceof because AbstractEntityPersister is the only one direct subclass of classMetadata"
		AbstractEntityPersister classMetadata = (AbstractEntityPersister) sessionFactory.getClassMetadata(entityClass);
		String[] propertyNames = classMetadata.getPropertyNames();
		
		Map<String, String> propertyNameToPropertyColumnName = new HashMap<>();
		propertyNameToPropertyColumnName.put("id", classMetadata.getIdentifierPropertyName());
		
		// List all property column
		for (int i = 0; i < propertyNames.length; i++) {
			String[] propertyColumnNames = classMetadata.getPropertyColumnNames(i);
			
			if(propertyColumnNames.length > 0) {
				// We do not handle properties on several columns here
				String propertyColumnName = propertyColumnNames[0];
				propertyNameToPropertyColumnName.put(propertyNames[i], propertyColumnName);
			}
		}
		
		for (String propertyName : propertyNameToPropertyColumnName.keySet()) {
			// Add property value to the map
			int indexOfPropertyInResultSet = getIndexOfPropertyInResultSet(propertyNameToPropertyColumnName.get(propertyName), aliases);
			
			if(indexOfPropertyInResultSet != -1) {
				map.put(propertyName, tuple[indexOfPropertyInResultSet]);
			}
		}
		
		return map;
	}

	// Return the index of the given property in the array of resultset aliases, or -1
	// if the property is not found
	private int getIndexOfPropertyInResultSet(String propertyColumnName, String[] aliases) {
		int result = -1;
		
		for (int i = 0; i < aliases.length; i++) {
			if(StringUtils.equalsIgnoreCase(propertyColumnName, aliases[i])) {
				result = i;
				break;
			}
		}
		
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List transformList(List collection) {
		return collection;
	}
}
