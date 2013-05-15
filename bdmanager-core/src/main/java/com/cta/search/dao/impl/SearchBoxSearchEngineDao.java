package com.cta.search.dao.impl;

import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.Delete;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.indices.CreateIndex;

import java.util.List;

import lombok.Data;
import lombok.SneakyThrows;

import org.elasticsearch.common.settings.ImmutableSettings;

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
	public void configureMapping() {
		ImmutableSettings.Builder settingsBuilder = ImmutableSettings.settingsBuilder();
		settingsBuilder.put("number_of_shards", 5);
		settingsBuilder.put("number_of_replicas", 1);

		jestClient.execute(new CreateIndex(SearchEngineConstants.INDEX_NAME, settingsBuilder.build().getAsMap()));
	}

	@Override
	@SneakyThrows
	public <T> List<Class<T>> search(String query, Class<T> clazz) {
		Search search = new Search(query);
		search.addIndex(SearchEngineConstants.INDEX_NAME);
		return jestClient.execute(search).getSourceAsObjectList(clazz);
	}

	@Override
	@SneakyThrows
	public void clearIndexType(String indexName, String typeName) {
		Delete deleteAction = new Delete.Builder().index(indexName).type(typeName).build();
		jestClient.execute(deleteAction);
	}

	private Index createSerieIndex(Serie serie) {
		return new Index.Builder(new IndexableSerie(serie)).index(SearchEngineConstants.INDEX_NAME)
				.type(SearchEngineConstants.SERIE_TYPE_NAME).build();
	}

	private Index createBdIndex(Bd bd) {
		return new Index.Builder(new IndexableBd(bd)).index(SearchEngineConstants.INDEX_NAME).type(SearchEngineConstants.BD_TYPE_NAME).build();
	}
}
