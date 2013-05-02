package com.cta.search;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.ClientConfig;
import io.searchbox.client.config.ClientConstants;
import io.searchbox.core.Search;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public class ElasticSearchClient {

	public static void main(String[] args) throws InterruptedException, IOException {
		// Configuration
		ClientConfig clientConfig = new ClientConfig();
		LinkedHashSet<String> servers = new LinkedHashSet<String>();
		servers.add("http://api.searchbox.io/api-key/d14ea906fbf37c8289a9d921ac18a0d2");
		clientConfig.getServerProperties().put(ClientConstants.SERVER_LIST,servers);

		// Construct a new Jest client according to configuration via factory
		JestClientFactory factory = new JestClientFactory();
		factory.setClientConfig(clientConfig);
		JestClient client = factory.getObject();
		
		
		QueryBuilder queryBuilder = QueryBuilders.queryString("First");
		
		Search search = new Search(queryBuilder);
		search.addIndex("bdmanager");
		search.addType("bd");

		JestResult result = client.execute(search);
		System.out.println(result.getJsonString());
		List<Object> articles = result.getSourceAsObjectList(Object.class);
		System.out.println(articles);
		
//		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "bdmanager").build();
//		Client client2 = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("http://api.searchbox.io/api-key/d14ea906fbf37c8289a9d921ac18a0d2", 80));
//		
//		SearchRequestBuilder searchRequest=client.prepareSearch("test");
//	       
//	       // Execute la requête
//	       SearchResponse searchResponse=searchRequest.execute().actionGet();
//	       
//	       // Extrait les résultats
//	       SearchHit[] searchHits = searchResponse.getHits().getHits();
//	       System.out.println(searchHits);
//	       client.close();
	}
}
