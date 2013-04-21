package com.cta.web.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.commons.lang3.exception.ExceptionUtils;

@Data
@EqualsAndHashCode(callSuper=true)
public class DetailedExceptionInfo extends ExceptionInfo {

	protected String stackTrace;
	
	public DetailedExceptionInfo(String code, String message, Throwable throwable) {
		super(code, message);
		this.stackTrace = ExceptionUtils.getStackTrace(throwable);
	}
}
