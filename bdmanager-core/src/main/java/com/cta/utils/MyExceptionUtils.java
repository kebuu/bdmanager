package com.cta.utils;

import com.cta.exception.AppException;

public abstract class MyExceptionUtils {

	public static AppException unhandle(Throwable throwable) {
		return new AppException(throwable);
	}
	
	public static void throwIfNull(Object object, AppException exception) {
		if(object == null) {
			throw exception;
		}
	}
	
	public static void throwIfFalse(boolean condition, AppException exception) {
		if(!condition) {
			throw exception;
		}
	}
}
