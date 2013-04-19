package com.cta.batch.launcher;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestBatch {

    public static void main(String[] args) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/batch-context.xml");
        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher", JobLauncher.class);
        Job job = (Job) context.getBean("helloWorldJob", Job.class);
        jobLauncher.run(job, new JobParameters());
    }
}
