package com.cta.batch.writer;

import io.searchbox.core.Bulk;

import java.util.List;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.item.ItemWriter;

import com.cta.model.Bd;
import com.cta.model.Serie;
import com.cta.search.dao.SearchEngineDao;

@Slf4j
@Setter
public class SerieAndBdToElasticSearchWriter implements ItemWriter<Serie> {

	protected SearchEngineDao searchEngineDao;
	
	@Override
	public void write(List<? extends Serie> items) throws Exception {
		log.info(">>>>> Start writing series to ES.");
		
		Bulk bulk = new Bulk();

		for (Serie serie : items) {
			// Add serie to index
			searchEngineDao.indexBulk(serie, bulk);
			
			for (Bd bd : serie.getBds()) {
				// Add bd to index
				searchEngineDao.indexBulk(bd, bulk);
			}
		}
		
		searchEngineDao.executeBulk(bulk);
		log.info(">>>>> End writing series to ES.");
	}
}
