package com.cta.tools.validator;

import org.hibernate.Session;

import com.cta.dto.crud.validation.ValidationResult;
import com.cta.model.Serie;

public class SerieValidator extends AbstractValidator {

	@Override
	public ValidationResult validate(Object data) {
		Serie serie = cast(data, Serie.class);
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.createCriteria(Serie.class);
		
		
		return null;
	}

	@Override
	public Class<?> getValidatedClass() {
		return Serie.class;
	}
}
