package com.cta.web.exception;

import java.util.Arrays;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.google.common.base.Joiner;

@Data
@EqualsAndHashCode(callSuper=true)
public class DetailedExceptionInfo extends ExceptionInfo {

	protected String stackTrace;
	
	public DetailedExceptionInfo(String code, String message, Throwable throwable) {
		super(code, message);
		String stackTracePart = Joiner.on("").join(Arrays.copyOf(ExceptionUtils.getRootCauseStackTrace(throwable), 5));
		this.stackTrace = stackTracePart.replaceAll("\\t", " ");
	}
}
