package com.cta.web;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public abstract class BdManagerWebConfigurerHelper {

	public static XmlWebApplicationContext configureSpringContext(ServletContext servletContext) {
		final XmlWebApplicationContext applicationContext = new XmlWebApplicationContext();
		applicationContext.setServletContext(servletContext);
		applicationContext.setConfigLocation("classpath:spring/web-main-context.xml");
		applicationContext.refresh();
		applicationContext.registerShutdownHook();
		return applicationContext;
	}

	public static Dynamic configureSpringSecurityFilter(ServletContext servletContext) {
		FilterRegistration.Dynamic springSecurityFilter = servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy());
        springSecurityFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), true, "/*");
        return springSecurityFilter;
	}
	
	public static DispatcherServlet createDispatcherServlet(WebApplicationContext webApplicationContext) {
		DispatcherServlet dispatcherServlet = new DispatcherServlet(webApplicationContext);
		dispatcherServlet.setDispatchOptionsRequest(true);
		return dispatcherServlet;
	}
}
