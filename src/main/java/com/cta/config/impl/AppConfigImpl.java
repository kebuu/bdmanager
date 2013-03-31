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
