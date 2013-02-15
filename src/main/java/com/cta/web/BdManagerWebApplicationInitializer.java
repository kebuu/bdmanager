package com.cta.web;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class BdManagerWebApplicationInitializer implements WebApplicationInitializer {

	public void onStartup(ServletContext servletContext) throws ServletException {
		
		FilterRegistration.Dynamic springSecurityFilter = servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy());
        springSecurityFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), true, "/*");
        
		XmlWebApplicationContext dispatcherContext = new XmlWebApplicationContext();
		dispatcherContext.setServletContext(servletContext);
		String configLocation = "classpath:spring/web-context.xml";
		dispatcherContext.setConfigLocation(configLocation);
		dispatcherContext.refresh();
		
		servletContext.addListener(new ContextLoaderListener(dispatcherContext));

		DispatcherServlet dispatcherServlet = new DispatcherServlet(dispatcherContext);
		dispatcherServlet.setDispatchOptionsRequest(true);

		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}
}
