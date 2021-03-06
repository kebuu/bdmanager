package com.cta.dto.crud.validation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.Data;

import com.google.common.collect.Lists;

@Data
public class ValidationResult {

	protected boolean ok = true;
	protected Map<String, List<String>> errorMessagesByProperty = new HashMap<String, List<String>>();
	
	public static ValidationResult ok() {
	    return new ValidationResult();
	}
	
	public static ValidationResult error() {
	    ValidationResult validationResult = new ValidationResult();
	    validationResult.setOk(false);
	    return validationResult;
	}
	
	public static ValidationResult error(Map<String, List<String>> errorMessagesByProperty) {
	    ValidationResult validationResult = error();
	    validationResult.setErrorMessagesByProperty(errorMessagesByProperty);
        return validationResult;
	}
	
	public static ValidationResult error(String property, String message) {
	    ValidationResult validationResult = error();
	    validationResult.getErrorMessagesByProperty().put(property, Lists.newArrayList(message));
	    return validationResult;
	}
	
	public ValidationResult addError(String property, String message) {
	    ok = false;
	    List<String> errorList = errorMessagesByProperty.get(property);
	    if(errorList == null) {
	        errorList = Lists.newArrayList();
	        errorMessagesByProperty.put(property, errorList);
	    }
	    errorList.add(message);
	    
	    return this;
	}

	public ValidationResult addErrors(Map<String, List<String>> errorMessagesByProperty) {
		ok = false;
		
		for (Entry<String, List<String>> entry : errorMessagesByProperty.entrySet()) {
			List<String> errorList = errorMessagesByProperty.get(entry.getKey());
			if(errorList == null) {
				errorList = Lists.newArrayList();
				errorMessagesByProperty.put(entry.getKey(), errorList);
			}
			errorList.addAll(entry.getValue());
		}
		
		return this;
		
	}
}
