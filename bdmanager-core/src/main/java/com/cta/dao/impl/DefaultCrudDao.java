package com.cta.dao.impl;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.hibernate.Session;

import com.cta.dao.CrudDao;
import com.cta.dto.crud.CrudResult;
import com.cta.dto.crud.CrudResult.CrudOperation;

@Slf4j
public class DefaultCrudDao extends AbstractDao implements CrudDao {

	@Override
	public CrudResult create(Object resource) {
		return crudOperation(resource, CrudOperation.CREATE, new CrudAction() {
			
			@Override
			public void process(Session session, Object resource) {
				getSession().persist(resource);
			}
		});
	}

	@Override
	public CrudResult update(Object resource) {
		return crudOperation(resource, CrudOperation.UPDATE, new CrudAction() {
			
			@Override
			public void process(Session session, Object resource) {
				getSession().update(resource);
			}
		});
	}

	@Override
	public CrudResult delete(Object resource) {
		return crudOperation(resource, CrudOperation.DELETE, new CrudAction() {
			
			@Override
			public void process(Session session, Object resource) {
				Object mergedResource = getSession().merge(resource);
				getSession().delete(mergedResource);
			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<? extends Object> list(String resourceClassName) {
		return getSession().createQuery("from " + resourceClassName).list();
	}
	
	@Override
	public Object get(Class<?> resourceClass, Long id) {
		return getSession().get(resourceClass, id);
	}
	
	protected CrudResult crudOperation(Object resource, CrudOperation crudOperation, CrudAction crudAction) {
		CrudResult result = new CrudResult(); 
		result.setOperation(crudOperation);
		result.setData(resource);
		
		try {
			crudAction.process(getSession(), resource);
		} catch (Exception e) {
			result.setOk(false);
			result.setMessage(e.getMessage());
			log.warn(e.getMessage(), e);
		}
		
		return result;
	}
	
	public interface CrudAction {
		public void process(Session session, Object resource);
	}
}
