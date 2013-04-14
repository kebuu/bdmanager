package com.cta.tools.i18n;

import java.util.Locale;

import lombok.Setter;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

@Setter
public class MessageSourceManager implements MessageSource, ImprovedMessageSource {

	protected MessageSource messageSource;
	protected LocaleService localeService;
	
	@Override
	public String getMessage(String code, Object[] args, String defaultMessage) {
		return messageSource.getMessage(code, args, defaultMessage, localeService.getLocale());
	}
	@Override
	public String getMessage(String code, Object[] args) throws NoSuchMessageException {
		return messageSource.getMessage(code, args, localeService.getLocale());
	}
	@Override
	public String getMessage(MessageSourceResolvable resolvable) throws NoSuchMessageException {
		return messageSource.getMessage(resolvable, localeService.getLocale());
	}
	
	public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
		Locale realLocale = getLocale(locale);
		return messageSource.getMessage(code, args, defaultMessage, realLocale);
	}
	public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
		Locale realLocale = getLocale(locale);
		return messageSource.getMessage(code, args, realLocale);
	}
	public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
		Locale realLocale = getLocale(locale);
		return messageSource.getMessage(resolvable, realLocale);
	}
	
	private Locale getLocale(Locale locale) {
		if(locale == null) {
			return localeService.getLocale();
		} else {
			return locale;
		}
	}
}
