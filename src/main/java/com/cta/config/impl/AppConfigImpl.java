package com.cta.config.impl;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.cta.config.AppConfig;

@ManagedResource
public class AppConfigImpl implements AppConfig {

	protected boolean debugModeActive;
	
	@ManagedAttribute
	public boolean isDebugModeActive() {
		return debugModeActive;
	}

	@ManagedAttribute
	public void setDebugModeActive(boolean debugModeActive) {
		this.debugModeActive = debugModeActive;
	}
}
