package com.cta.exception;

public class TooManyResultsException extends AppException {

	private static final long serialVersionUID = 1L;

	public TooManyResultsException() {
		super("too.many.result.exception");
	}

	public TooManyResultsException(String code, Object... arguments) {
		super(code, arguments);
	}

	public TooManyResultsException(String code, Throwable cause, Object... arguments) {
		super(code, cause, arguments);
	}

	public TooManyResultsException(Throwable cause) {
		super(cause);
	}
}
