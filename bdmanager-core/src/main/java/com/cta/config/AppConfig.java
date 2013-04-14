package com.cta.config;

import java.util.List;


public interface AppConfig {

	boolean isDebugModeActive();

	List<String> getDateConverterFormats();
}
