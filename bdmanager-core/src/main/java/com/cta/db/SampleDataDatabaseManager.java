package com.cta.db;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.sql.DataSource;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.Charsets;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Slf4j
public class SampleDataDatabaseManager implements InitializingBean {

	@Setter
	protected boolean loadSampleData;
	@Setter
	protected DataSource dataSource;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (loadSampleData) {
			log.info("Loading sample data");
			loadSampleData();
		}
	}

	/**
	 * Load sample data
	 */
	protected void loadSampleData() {
		try {
			ResourceDatabasePopulator pop = new ResourceDatabasePopulator();
			pop.setSqlScriptEncoding(Charsets.UTF_8.name());

			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			List<Resource> resources = Arrays.asList(resolver.getResources("classpath:sample/data/sql/*.sql"));

			Collections.sort(resources, new ResourceComparator());

			for (Resource resource : resources) {
				pop.addScript(resource);
			}

			DatabasePopulatorUtils.execute(pop, dataSource);
		} catch (IOException e) {
			log.warn("Cannot load sample data", e);
		}
	}

	/**
	 * Comparator for spring resource api
	 */
	protected final class ResourceComparator implements Comparator<Resource> {
		@Override
		public int compare(Resource o1, Resource o2) {
			try {
				if (o1.getFilename() != null) {
					return o1.getFilename().compareTo(o2.getFilename());
				} else if (o1.getURI() != null) {
					return o1.getURI().compareTo(o2.getURI());
				} else {
					return 0;
				}
			} catch (IOException e) {
				return 0;
			}
		}
	}
}