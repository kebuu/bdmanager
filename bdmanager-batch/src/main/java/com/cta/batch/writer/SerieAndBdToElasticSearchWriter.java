package com.cta.batch.writer;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.ClientConfig;
import io.searchbox.client.config.ClientConstants;
import io.searchbox.core.Index;

import java.util.LinkedHashSet;
import java.util.List;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.item.ItemWriter;

import com.cta.model.Bd;
import com.cta.model.Serie;
import com.cta.search.IndexableBd;
import com.cta.search.IndexableSerie;
import com.cta.search.SearchConstants;

@Slf4j
@Setter
public class SerieAndBdToElasticSearchWriter implements ItemWriter<Serie> {

	protected JestClient jestClient;
	
	public SerieAndBdToElasticSearchWriter() {
		// Configuration
		ClientConfig clientConfig = new ClientConfig();
		LinkedHashSet<String> servers = new LinkedHashSet<String>();
		servers.add("http://api.searchbox.io/api-key/d14ea906fbf37c8289a9d921ac18a0d2");
		clientConfig.getServerProperties().put(ClientConstants.SERVER_LIST,servers);

		// Construct a new Jest client according to configuration via factory
		JestClientFactory factory = new JestClientFactory();
		factory.setClientConfig(clientConfig);
		jestClient = factory.getObject();
	}
	
	@Override
	public void write(List<? extends Serie> items) throws Exception {
		for (Serie serie : items) {
			// Add serie to index
			addSerieToIndex(serie);
			
			for (Bd bd : serie.getBds()) {
				// Add bd to index
				addBdToIndex(bd);
			}
		}
	}

	private void addBdToIndex(Bd bd) {
		try {
			log.trace(">>>>> Adding bd to index : " + bd);
			Index index = new Index.Builder(new IndexableBd(bd)).index(SearchConstants.INDEX_NAME).type(SearchConstants.BD_TYPE_NAME).build();
			jestClient.execute(index);
		} catch (Exception e) {
			log.warn("Cannot index bd : " + bd, e);
		}
	}

	private void addSerieToIndex(Serie serie) {
		try {
			log.trace(">>>>> Adding serie to index : " + serie);
			Index index = new Index.Builder(new IndexableSerie(serie)).index(SearchConstants.INDEX_NAME).type(SearchConstants.SERIE_TYPE_NAME).build();
			jestClient.execute(index);
		} catch (Exception e) {
			log.warn("Cannot index serie : " + serie, e);
		}
	}
}
