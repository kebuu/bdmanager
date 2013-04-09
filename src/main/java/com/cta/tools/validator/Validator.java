package com.cta.tools.validator;

import com.cta.dto.crud.validation.ValidationResult;

public interface Validator {

	ValidationResult validate(Object data);
	
	boolean canValidate(Class<?> clazz);
	
	Class<?> getValidatedClass();
}
