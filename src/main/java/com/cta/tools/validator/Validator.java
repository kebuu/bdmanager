package com.cta.tools.validator;

import org.springframework.transaction.annotation.Transactional;

import com.cta.dto.crud.validation.ValidationResult;

public interface Validator {

    @Transactional(readOnly=true)
	ValidationResult validate(Object data);
	
	Class<?> getValidatedClass();
}
