package com.cta.config.impl;

import java.util.List;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.cta.config.AppConfig;
import com.google.common.collect.Lists;

@ManagedResource
public class AppConfigImpl implements AppConfig {

	protected List<String> dateConverterFormats = Lists.newArrayList("yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss");
	protected boolean debugModeActive;
	protected String crossDomainAllowedHeaders;
	protected String crossDomainAllowedMethods;
	protected String crossDomainAllowedOrigins;
	protected String crossDomainMaxAge;
	
	public String getCrossDomainAllowedHeaders() {
		return crossDomainAllowedHeaders;
	}

	public void setCrossDomainAllowedHeaders(String crossDomainAllowedHeaders) {
		this.crossDomainAllowedHeaders = crossDomainAllowedHeaders;
	}

	public String getCrossDomainAllowedMethods() {
		return crossDomainAllowedMethods;
	}

	public void setCrossDomainAllowedMethods(String crossDomainAllowedMethods) {
		this.crossDomainAllowedMethods = crossDomainAllowedMethods;
	}

	public String getCrossDomainAllowedOrigins() {
		return crossDomainAllowedOrigins;
	}

	public void setCrossDomainAllowedOrigins(String crossDomainAllowedOrigins) {
		this.crossDomainAllowedOrigins = crossDomainAllowedOrigins;
	}

	public String getCrossDomainMaxAge() {
		return crossDomainMaxAge;
	}

	public void setCrossDomainMaxAge(String crossDomainMaxAge) {
		this.crossDomainMaxAge = crossDomainMaxAge;
	}

	@ManagedAttribute
	public List<String> getDateConverterFormats() {
		return dateConverterFormats;
	}

	@ManagedAttribute
	public void setDateConverterFormats(List<String> dateConverterFormats) {
		this.dateConverterFormats = dateConverterFormats;
	}

	@ManagedAttribute
	public boolean isDebugModeActive() {
		return debugModeActive;
	}

	@ManagedAttribute
	public void setDebugModeActive(boolean debugModeActive) {
		this.debugModeActive = debugModeActive;
	}
}
