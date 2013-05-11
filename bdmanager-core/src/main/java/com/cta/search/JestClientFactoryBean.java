package com.cta.search;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.ClientConfig;
import io.searchbox.client.config.ClientConstants;
import io.searchbox.client.http.JestHttpClient;

import java.util.LinkedHashSet;

import lombok.Setter;

import org.springframework.beans.factory.config.AbstractFactoryBean;

@Setter
public class JestClientFactoryBean extends AbstractFactoryBean<JestClient> {

	protected String searchBoxApiKey;
	
	@Override
	public Class<?> getObjectType() {
		return JestHttpClient.class;
	}

	@Override
	protected JestClient createInstance() throws Exception {
		// Configuration
		ClientConfig clientConfig = new ClientConfig();
		LinkedHashSet<String> servers = new LinkedHashSet<String>();
		servers.add("http://api.searchbox.io/api-key/" + searchBoxApiKey);
		clientConfig.getProperties().put(ClientConstants.SERVER_LIST,servers);

		// Construct a new Jest client according to configuration via factory
		JestClientFactory factory = new JestClientFactory();
		factory.setClientConfig(clientConfig);
		return factory.getObject();
	}

}
