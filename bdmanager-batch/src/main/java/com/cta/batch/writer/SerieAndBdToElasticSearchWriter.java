package com.cta.batch.writer;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.item.ItemWriter;

import com.cta.model.Bd;
import com.cta.model.Serie;

@Slf4j
public class SerieAndBdToElasticSearchWriter implements ItemWriter<Serie> {

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
		log.info("Adding bd " + bd.getTitle());
	}

	private void addSerieToIndex(Serie serie) {
		log.info(">>>>> Adding serie " + serie.getName());
	}
}
