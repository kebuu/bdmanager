package com.cta.web.i18n;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Setter;

import org.springframework.web.servlet.LocaleResolver;

import com.google.common.collect.Lists;

@Setter
public class CompositeLocaleResolver implements LocaleResolver {

	protected List<LocaleResolver> localResolvers = Lists.newArrayList();
	
	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		Locale newLocale = Locale.getDefault();
		
		for (LocaleResolver localResolver : localResolvers) {
			newLocale = localResolver.resolveLocale(request);
			
			if(!newLocale.equals(Locale.getDefault())) {
				break;
			}
		}
		
		return newLocale;
	}

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		//
	}
}
