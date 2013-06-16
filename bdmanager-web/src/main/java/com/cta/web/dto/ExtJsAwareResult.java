package com.cta.web.dto;

import lombok.Data;

@Data
public class ExtJsAwareResult {

	protected boolean success = true;
	protected Object data;
	protected String errorMessage;
	
	public static ExtJsAwareResult withData(Object data) {
		ExtJsAwareResult extJsAwareResult = new ExtJsAwareResult();
		extJsAwareResult.data = data;
		return extJsAwareResult;
	}
	
	public static ExtJsAwareResult withErrorMessage(String errorMessage) {
		ExtJsAwareResult extJsAwareResult = new ExtJsAwareResult();
		extJsAwareResult.errorMessage = errorMessage;
		extJsAwareResult.success = false;
		return extJsAwareResult;
	}
}
