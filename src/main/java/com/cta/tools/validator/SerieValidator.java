package com.cta.tools.validator;

import com.cta.dto.crud.validation.ValidationResult;
import com.cta.model.Serie;

public class SerieValidator extends AbstractValidator {

	public static void main(String[] args) {
		System.out.println(new SerieValidator().canValidate(Serie.class));
	}

	@Override
	public ValidationResult validate(Object data) {
		if(data instanceof Serie) {
			Serie serie = (Serie) data;
			sessionFactory.getCurrentSession();
			return null;
		} else {
			return null;
		}
	}

	@Override
	public Class<?> getValidatedClass() {
		return Serie.class;
	}
}
