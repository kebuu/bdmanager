package com.cta.service;

import com.cta.dto.crud.validation.ValidationResult;

public interface ValidationService {

	 ValidationResult validate(Object data);
}
