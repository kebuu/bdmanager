 package com.cta.web.interceptor;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Setter;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cta.tools.threadlocal.AbstractThreadLocalService;

@Setter
public class CustomLocaleChangeInterceptor extends HandlerInterceptorAdapter {

	protected LocaleResolver localeResolver;
	protected AbstractThreadLocalService<Locale> localeService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Locale locale = localeResolver.resolveLocale(request);
		localeService.setData(locale);
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		localeService.removeData();
	}
}
