package com.cta.batch.writer;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.item.ItemWriter;

/**
 * Writer for testing purpose
 */
@Slf4j
public class LogWriter implements ItemWriter<Object> {

	@Override
	public void write(List<? extends Object> items) throws Exception {
		for (Object object : items) {
			log.info(object.toString());
		}
	}
}
