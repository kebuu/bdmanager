package com.cta.tools.validator;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import com.cta.dto.crud.validation.ValidationResult;
import com.cta.model.Serie;

public class SerieValidator extends AbstractValidator {

	@Override
	public ValidationResult validate(Object data) {
	    ValidationResult result = ValidationResult.ok();
	    
		Serie serie = cast(data, Serie.class);
		Session currentSession = sessionFactory.getCurrentSession();
		Serie existingSerie = (Serie) currentSession.createCriteria(Serie.class)
		        .add(Restrictions.eq("name", serie).ignoreCase())
		        .createCriteria("bds", JoinType.NONE)
		        .uniqueResult();
		
		if(existingSerie != null && !serie.equals(existingSerie)) {
		    result.addError("name", messageSource.getMessage("", null));
		}
		
		return result;
	}

	@Override
	public Class<?> getValidatedClass() {
		return Serie.class;
	}
}
