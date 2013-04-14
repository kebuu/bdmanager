package com.cta.web.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;

import com.cta.dto.crud.validation.ValidationResult;

@Getter
public class ValidationServiceExceptionInfo extends ExceptionInfo {

	protected Map<String, List<String>> validationMessages = new HashMap<String, List<String>>();
	
	public ValidationServiceExceptionInfo(String code, String message, ValidationResult validationResult) {
		super(code, message);
		this.validationMessages.putAll(validationResult.getErrorMessagesByProperty());
	}
}
