package com.cta.web.service;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import com.cta.web.BdManagerEmbededServer;

@Slf4j
@Setter
public class DefaultShutdownService implements ShutdownService {

	protected BdManagerEmbededServer server;
	
	@Override
	public void shutdown() {
		new Thread(new Runnable() {
			// Use a thread because it is not possible to stop
			
			@Override
			public void run() {
				if(server != null) {
					log.info("Shutting down server");
					server.stop();
				}
			}
		}).start();
	}
}
