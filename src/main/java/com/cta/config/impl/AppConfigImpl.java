package com.cta.config.impl;

import com.cta.config.AppConfig;

import lombok.Data;

@Data
public class AppConfigImpl implements AppConfig {

	protected boolean debugModeActive;

}
