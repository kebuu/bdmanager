package com.cta.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cta.config.AppConfig;

/**
 * Adds the appropriate headers to the response for Cross-Origin Resource
 * Sharing to work.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CrossDomainInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	protected AppConfig appConfig;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(!request.getMethod().equalsIgnoreCase("OPTIONS")) {
			response.addHeader("Access-Control-Allow-Origin", "*");
		}
		return true;
	}
}
