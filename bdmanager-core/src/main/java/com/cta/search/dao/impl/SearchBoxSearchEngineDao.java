package com.cta.search.dao.impl;

import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.Delete;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.PutMapping;

import java.util.List;

import lombok.Data;
import lombok.SneakyThrows;

import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;

import com.cta.model.Bd;
import com.cta.model.Serie;
import com.cta.search.IndexableBd;
import com.cta.search.IndexableSerie;
import com.cta.search.SearchEngineConstants;
import com.cta.search.dao.SearchEngineDao;

@Data
public class SearchBoxSearchEngineDao implements SearchEngineDao {

	protected JestClient jestClient;

	@Override
	@SneakyThrows
	public void index(Serie serie) {
		jestClient.execute(createSerieIndex(serie));
	}

	@Override
	@SneakyThrows
	public void index(Bd bd) {
		jestClient.execute(createBdIndex(bd));
	}

	@Override
	public void indexBulk(Serie serie, Bulk bulk) {
		bulk.addIndex(createSerieIndex(serie));
	}

	@Override
	public void indexBulk(Bd bd, Bulk bulk) {
		bulk.addIndex(createBdIndex(bd));
	}

	@Override
	@SneakyThrows
	public void executeBulk(Bulk bulk) {
		jestClient.execute(bulk);
	}

	@Override
	@SneakyThrows
	public void configureTypeMapping(String indexName, String typeName, String typeMapping) {
		PutMapping putMapping = new PutMapping(indexName, typeName, typeMapping);
		jestClient.execute(putMapping);
	}
	
	@Override
	@SneakyThrows
	public <T> List<Class<T>> search(String query, Class<T> clazz) {
		Search search = new Search(query);
		return jestClient.execute(search).getSourceAsObjectList(clazz);
	}
	
	@Override
	@SneakyThrows
	public String search(String query) {
		Search search = new Search(query);
		return jestClient.execute(search).getJsonString();
	}

	@Override
	@SneakyThrows
	public void clearIndexType(String indexName, String typeName) {
		Delete deleteAction = new Delete.Builder().index(indexName).type(typeName).build();
		jestClient.execute(deleteAction);
	}
	
	@Override
	@SneakyThrows
	public void createIndex(String indexName, String jsonConfig) {
		Settings setting = ImmutableSettings.settingsBuilder().loadFromSource(jsonConfig).build();
		jestClient.execute(new CreateIndex(indexName, setting.getAsMap()));
	}

	private Index createSerieIndex(Serie serie) {
		return new Index.Builder(new IndexableSerie(serie)).index(SearchEngineConstants.INDEX_NAME).type(SearchEngineConstants.SERIE_TYPE_NAME).build();
	}

	private Index createBdIndex(Bd bd) {
		return new Index.Builder(new IndexableBd(bd)).index(SearchEngineConstants.INDEX_NAME).type(SearchEngineConstants.BD_TYPE_NAME).build();
	}
}
