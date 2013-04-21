package com.cta.batch;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.item.ItemWriter;

import com.cta.batch.model.BdFromCsv;

@Slf4j
public class FakeBdWriter implements ItemWriter<BdFromCsv> {

	@Override
	public void write(List<? extends BdFromCsv> items) throws Exception {
		for (BdFromCsv bdFromCsv : items) {
			log.info(bdFromCsv.getTitre());
		}
	}
}
