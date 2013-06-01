package com.cta.config;

import java.util.List;


public interface AppConfig {

	boolean isDebugModeActive();

	List<String> getDateConverterFormats();
	
	String getCrossDomainAllowedHeaders();
	
	String getCrossDomainAllowedMethods();
	
	String getCrossDomainAllowedOrigins();
	
	String getCrossDomainMaxAge();
}
