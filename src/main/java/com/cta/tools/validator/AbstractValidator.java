package com.cta.tools.validator;

import lombok.Setter;

import org.hibernate.SessionFactory;

import com.cta.tools.i18n.MessageSourceManager;

@Setter
public abstract class AbstractValidator implements Validator {

	protected SessionFactory sessionFactory;
	protected MessageSourceManager messageSource;
	
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
