package com.cta.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.cta.dto.crud.validation.ValidationResult;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ValidationServiceException extends AppException {

	private static final long serialVersionUID = 1L;

	public static final String DEFAULT_VALIDATION_EXCEPTION_CODE = "validation.exception";

	protected ValidationResult validationResult;

	public ValidationServiceException(ValidationResult validationResult) {
		super(DEFAULT_VALIDATION_EXCEPTION_CODE);
		this.validationResult = validationResult;
	}

	public ValidationServiceException(ValidationResult validationResult, String code, Object... arguments) {
		this(validationResult);
		this.code = code;
		this.arguments = arguments;
	}
}
