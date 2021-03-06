package com.cta.batch.launcher;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestBatch {

	public static void main(String[] args) throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/main-context.xml", "spring/batch-context.xml");
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher", JobLauncher.class);

		// Simple HelloWorld
		Job job = (Job) context.getBean("helloWorldJob", Job.class);
//		jobLauncher.run(job, new JobParameters());

		job = (Job) context.getBean("fillSerieAndBdFromCsv", Job.class);
		jobLauncher.run(job, new JobParametersBuilder().addString("test", "test").toJobParameters());
		
//		job = (Job) context.getBean("fromDatabaseToElasticSearch", Job.class);
//		jobLauncher.run(job, new JobParameters());
		
		context.close(); // otherwise the process does not stop
	}
}
