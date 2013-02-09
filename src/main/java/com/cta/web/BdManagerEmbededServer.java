package com.cta.web;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.cli.CommandLine;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


@Slf4j
public abstract class BdManagerEmbededServer extends AbstractMain {
	
	static {
		options.addOption("h", "help", false, "Affiche l'aide de la ligne de commande");
		options.addOption("p", "port", false, "Indique le port http (8080 par defaut)");
		options.addOption("c", "context-path", false, "Indique le nom du contexte path (/ par defaut).");
	}
		
	public static void main(String[] args) {
		CommandLine cmd = parseCommandLine(args);
		
		if(cmd.hasOption('h')) {
			displayUsage("BdManagerEmbededServer");
		} else {
			int port = getOptionValueInt(cmd, "p", 8082);
			String contextPath = getOptionValueString(cmd, "c", "/");
			
			log.info("Starting on port : " + port);
			log.info("Starting on context path : " + contextPath);
			
			try {
				Server server = new Server(port);
				ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
				context.setContextPath(contextPath);
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
}
