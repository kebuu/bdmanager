package com.cta.tools.validator;

import lombok.Setter;

import org.hibernate.SessionFactory;

@Setter
public abstract class AbstractValidator implements Validator {

	protected SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
    protected <T> T cast(Object data, Class<T> clazz) {
	    try {
            return (T) data;
        } catch (ClassCastException e) {
            //TODO Améliorer la gestion d'erreur
            throw new RuntimeException();
        }
	}
}
