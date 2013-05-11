package com.cta.batch.tasklet;

import lombok.Setter;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.cta.search.SearchEngineConstants;
import com.cta.search.dao.SearchEngineDao;

@Setter
public class ClearElasticSearchIndexTasklet implements Tasklet {

	protected SearchEngineDao SearchEngineDao;
	
    @Override
    public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
        SearchEngineDao.clearIndexType(SearchEngineConstants.INDEX_NAME, SearchEngineConstants.SERIE_TYPE_NAME);
        SearchEngineDao.clearIndexType(SearchEngineConstants.INDEX_NAME, SearchEngineConstants.BD_TYPE_NAME);
        return RepeatStatus.FINISHED;
    }
}
