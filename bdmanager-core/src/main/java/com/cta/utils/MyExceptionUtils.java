package com.cta.utils;

import com.cta.exception.AppException;

public abstract class MyExceptionUtils {

	public static AppException unhandle(Throwable throwable) {
		return new AppException(throwable);
	}
}
