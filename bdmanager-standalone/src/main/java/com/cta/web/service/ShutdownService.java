package com.cta.web.service;

import com.cta.web.BdManagerEmbededServer;

public interface ShutdownService {

	void shutdown();
	void setServer(BdManagerEmbededServer server);
}
