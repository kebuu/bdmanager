package com.cta.dto.crud.validation;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class ValidationResult {

	protected boolean ok = true;
	protected Map<String, List<String>> errorMessagesByProperty;
}
