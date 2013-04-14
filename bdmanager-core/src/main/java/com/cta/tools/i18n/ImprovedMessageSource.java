package com.cta.tools.i18n;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

public interface ImprovedMessageSource {

	public abstract String getMessage(MessageSourceResolvable resolvable) throws NoSuchMessageException;

	public abstract String getMessage(String code, Object[] args) throws NoSuchMessageException;

	public abstract String getMessage(String code, Object[] args, String defaultMessage);

}
