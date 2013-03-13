package com.cta;

import javax.sql.DataSource;

import org.apache.commons.io.Charsets;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:spring/test-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class BaseSpringTest {

	@Autowired
	protected DataSource datasource;

	@Before
	public void setUpBaseSpringTest() {
		executeDatabaseScripts("/sql/clean-all-tables.sql");
		executeDatabaseScripts("/sql/test-serie.sql", "/sql/test-bd.sql");
	}

	/**
	 * Utilise les fichiers indiques pour remplir la base de donnees. Ces
	 * fichiers doivent etre dans le classpath.
	 */
	protected void executeDatabaseScripts(String... scriptFiles) {
		ResourceDatabasePopulator pop = new ResourceDatabasePopulator();
		pop.setSqlScriptEncoding(Charsets.UTF_8.name());

		for (String scriptFile : scriptFiles) {
			pop.addScript(new ClassPathResource(scriptFile));
		}

		DatabasePopulatorUtils.execute(pop, datasource);
	}
}
