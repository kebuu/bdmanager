package com.cta;

import javax.sql.DataSource;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations={"classpath:spring/test-context.xml"})
public class BaseSpringTest {

	@Autowired
	protected DataSource datasource;
	
	@Before
	public void setUpBaseSpringTest() {
		populatedDatabase("/sql/test-serie.sql", "/sql/test-bd.sql");
	}
	
	/**
	 * Utilise les fichiers indiques pour remplir la base de donnees.
	 * Ces fichiers doivent etre dans le classpath.
	 */
	protected void populatedDatabase(String... scriptFiles) {
		ResourceDatabasePopulator pop = new ResourceDatabasePopulator();
		
		for (String scriptFile : scriptFiles) {
			pop.addScript(new ClassPathResource(scriptFile));
		}
		
		DatabasePopulatorUtils.execute(pop, datasource);
	}
} 
