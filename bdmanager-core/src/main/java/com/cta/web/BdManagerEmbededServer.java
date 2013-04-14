package com.cta.web;

import javax.servlet.ServletContext;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.OptionBuilder;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.component.AbstractLifeCycle.AbstractLifeCycleListener;
import org.eclipse.jetty.util.component.LifeCycle;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.google.common.collect.ImmutableList;


@Slf4j
@SuppressWarnings("static-access")
public abstract class BdManagerEmbededServer extends AbstractMain {
	
	static {
		options.addOption("h", "help", false, "Affiche l'aide de la ligne de commande");
		options.addOption("p", "port", false, "Indique le port http (8090 par defaut)");
		options.addOption("c", "context-path", false, "Indique le nom du contexte path (/ par defaut).");
		options.addOption(OptionBuilder.withArgName("property=value" )
                .hasArgs(2)
                .withValueSeparator()
                .withDescription( "Proprietes systemes java classiques" )
                .create( "D" ));
	}
		
	public static void main(String[] args) {
		log.info("Starting with args : " + ImmutableList.copyOf(args));
		CommandLine cmd = parseCommandLine(args);
		
		if(cmd.hasOption('h')) {
			displayUsage("BdManagerEmbededServer");
		} else {
			int port = getOptionValueInt(cmd, "p", System.getProperty("app.port") == null ? 8090 : Integer.parseInt(System.getProperty("app.port"))); // Hack cloudbees
			String contextPath = getOptionValueString(cmd, "c", "/");
			
			log.info("Starting on port : " + port);
			log.info("Starting on context path : " + contextPath);
			
			Server server = null;
			try {
				// Create servlet context holder
				ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
				servletContextHandler.setContextPath(contextPath);
				
				// Create servlet context
				final ServletContext servletContext = servletContextHandler.getServletContext();
				
				// Configure spring security
				BdManagerWebConfigurerHelper.configureSpringSecurityFilter(servletContext);
				
				// Configure spring web context
				final XmlWebApplicationContext webApplicationContext = BdManagerWebConfigurerHelper.configureSpringContext(servletContext);
				webApplicationContext.registerShutdownHook();
				
				// Create dispatcher servlet
				DispatcherServlet dispatcherServlet = BdManagerWebConfigurerHelper.createDispatcherServlet(webApplicationContext);
				
				// Use dispatcher servlet in servlet context holder
				servletContextHandler.addServlet(new ServletHolder(dispatcherServlet), "/*");
				servletContextHandler.addLifeCycleListener(new AbstractLifeCycleListener() {
					
					@Override
					public void lifeCycleStarting(LifeCycle event) {
						servletContext.addListener(new ContextLoaderListener(webApplicationContext));
					}
				});
				
				// Start server
				server = new Server(port);
				server.setHandler(servletContextHandler);
				server.start();
				server.join();
			} catch (Exception e) {
				stopServer(server);
				throw new RuntimeException(e);
			} 
		}
	}

	private static void stopServer(Server server) {
		if(server != null) {
			try {
				server.stop();
			} catch (Exception e1) {
				// Nothing to do
			}
		}
	}	
}
