package com.cta.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class BdManagerEmbededServer {

	public static void main(String[] args) {

        try {
			int port = 8082;
			Server server = new Server(port);
			ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
			context.setContextPath("/");
			server.setHandler(context);

			XmlWebApplicationContext applicationContext = new XmlWebApplicationContext();
			applicationContext.setServletContext(context.getServletContext());
			applicationContext.setConfigLocation("classpath:spring/web-context.xml");
			applicationContext.refresh();
			applicationContext.registerShutdownHook();
			
			DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);
			dispatcherServlet.setDispatchOptionsRequest(true);
			context.addServlet(new ServletHolder(dispatcherServlet), "/*");
			
			server.start();
			server.join();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
}
