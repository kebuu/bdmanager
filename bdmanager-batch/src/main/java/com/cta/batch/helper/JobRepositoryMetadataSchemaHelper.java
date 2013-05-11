package com.cta.batch.helper;

import javax.sql.DataSource;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.support.DatabaseType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.google.common.base.Charsets;

@Slf4j
@Setter
public class JobRepositoryMetadataSchemaHelper implements InitializingBean {

	public static final String BATCH_JOB_INSTANCE_TABLE_NAME = "BATCH_JOB_INSTANCE";
	
	protected DataSource datasource;
	protected boolean dropBatchSchemaOnStartup;
	protected boolean createBatchSchemaOnStartup;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(dropBatchSchemaOnStartup || createBatchSchemaOnStartup) { // Not looking for database type if not needed
			DatabaseType databaseType = DatabaseType.fromMetaData(datasource);
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			
			ResourceDatabasePopulator pop = new ResourceDatabasePopulator();
			pop.setSqlScriptEncoding(Charsets.UTF_8.name());
			
			if(dropBatchSchemaOnStartup) {
				Resource dropScript = getDropScript(resolver, databaseType);
				
				if(dropScript == null) {
					log.warn("Cannot find drop batch script for database type " + databaseType + " (doing nothing)");
				} else {
					log.info("Batch tables will be dropped");
					pop.addScript(dropScript);
				}
			}
			
			if(createBatchSchemaOnStartup) {
				Resource createScript = getCreateScript(resolver, databaseType);
				
				if(createScript == null) {
					log.warn("Cannot find create batch script for database type " + databaseType + " (doing nothing)");
				} else {
					if(batchTablesSeemToAlreadyExist()) {
						log.info("It seems batch tables already exist.");
					} else {
						log.info("Batch tables will be created");
						pop.addScript(createScript);
					}
				}
			}
			
			DatabasePopulatorUtils.execute(pop, datasource);
		}
	}

	private boolean batchTablesSeemToAlreadyExist() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		try {
			jdbcTemplate.queryForInt("select count(*) from " + BATCH_JOB_INSTANCE_TABLE_NAME);
			return true; // If the query succeeded, it means batch table exists
		} catch (DataAccessException e) {
			return false; // If the query failed, it may be because batch tables does not exist
		}
	}

	private Resource getDropScript(PathMatchingResourcePatternResolver resolver, DatabaseType databaseType) {
		Resource createScript = null;
		if(databaseType.equals(DatabaseType.H2)) {
			createScript = resolver.getResource("classpath:db/schema-drop-h2.sql");
		} else if(databaseType.equals(DatabaseType.MYSQL)) {
			createScript = resolver.getResource("classpath:db/schema-drop-mysql.sql");
		}
		return createScript;
	}

	private Resource getCreateScript(PathMatchingResourcePatternResolver resolver, DatabaseType databaseType) {
		Resource dropScript = null;
		if(databaseType.equals(DatabaseType.H2)) {
			dropScript = resolver.getResource("classpath:db/schema-h2.sql");
		} else if(databaseType.equals(DatabaseType.MYSQL)) {
			dropScript = resolver.getResource("classpath:db/schema-mysql.sql");
		}
		return dropScript;
	}
}
