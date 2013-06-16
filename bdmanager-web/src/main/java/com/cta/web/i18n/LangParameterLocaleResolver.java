package com.cta.web.i18n;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Setter;

import org.apache.commons.lang3.LocaleUtils;
import org.springframework.web.servlet.LocaleResolver;

@Setter
public class LangParameterLocaleResolver implements LocaleResolver {

	protected String paramName = "lang";
	
	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		Locale newLocale = Locale.getDefault();
		
		String newLocaleString = request.getParameter(this.paramName);
		if (newLocaleString != null) {
			newLocale = LocaleUtils.toLocale(newLocaleString);
		}
		
		return newLocale;
	}

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		// Nothing to do
	}
}
