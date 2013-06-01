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
	protected String developperMessage;
	protected Object[] arguments;
	
	public AppException(Throwable cause) {
		super(cause);
	}

	public AppException(String code, Object... arguments) {
		this.code = code;
		this.arguments = arguments;
	}
	
	public AppException(String code, Throwable cause, Object... arguments) {
		super(cause);
		this.code = code;
		this.arguments = arguments;
	}
}
