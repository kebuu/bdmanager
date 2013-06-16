package com.cta.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AppException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public static final String DEFAULT_EXCEPTION_CODE = "unhandled.exception";
	
	protected String code = DEFAULT_EXCEPTION_CODE;
	protected String developerMessage;
	protected Object[] arguments;

	public static AppException developerOriented(String developerMessage) {
		AppException appException =  new AppException();
		appException.setDeveloperMessage(developerMessage);
		return appException;
	}
	
	public AppException(Throwable cause) {
		super(cause);
	}

	public AppException(String code, Object... arguments) {
		this.code = code;
		this.arguments = arguments;
	}
	
	public AppException(String code, Throwable cause, Object... arguments) {
		this(code, null, cause, arguments);
	}
	
	public AppException(String code, String developperMessage, Object... arguments) {
		this.code = code;
		this.arguments = arguments;
	}
	
	public AppException(String code, String developperMessage, Throwable cause, Object... arguments) {
		super(cause);
		this.code = code;
		this.arguments = arguments;
	}
}
