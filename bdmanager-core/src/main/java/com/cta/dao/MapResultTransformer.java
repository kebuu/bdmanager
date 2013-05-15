package com.cta.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Setter;

import org.hibernate.SessionFactory;
import org.hibernate.transform.ResultTransformer;

@Setter
public class MapResultTransformer implements ResultTransformer {

	private static final long serialVersionUID = 1L;
	
	protected SessionFactory sessionFactory;
	
	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Map<String, Object> map = new HashMap<>();
//		AbstractEntityPersister might be of some help. May be the getPropertyColumnNames 
		return map;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List transformList(List collection) {
		return collection;
	}
}
