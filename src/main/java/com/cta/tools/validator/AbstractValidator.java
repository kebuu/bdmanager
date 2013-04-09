package com.cta.tools.validator;

import lombok.Setter;

import org.hibernate.SessionFactory;

@Setter
public abstract class AbstractValidator implements Validator {

	protected SessionFactory sessionFactory;
	
	@Override
	public boolean canValidate(Class<?> clazz) {
		return clazz.isAssignableFrom(this.getValidatedClass());
	}
}
