package com.cta.batch.writer;

import java.util.List;
import java.util.Set;

import lombok.Setter;

import org.springframework.batch.item.ItemWriter;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.cta.batch.model.BdFromCsv;
import com.google.common.collect.Sets;

@Setter
public class SerieWriter implements ItemWriter<BdFromCsv> {

	protected HibernateTemplate hibernateTemplate;
	
	@Override
	public void write(List<? extends BdFromCsv> items) throws Exception {
		hibernateTemplate.find("");
		
		Set<String> series = Sets.newHashSet();
		
		for (BdFromCsv bdFromCsv : items) {
			String serieName = bdFromCsv.getSerie();
			if(!series.contains(serieName)) {
				
				series.add(serieName);
			}
		}
	}
}
