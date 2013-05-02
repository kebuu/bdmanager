package com.cta.web.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/batch")
public class JobLauncherController {
	
	private static final String JOB_PARAM_NAME = "job";
	
	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private JobRegistry jobRegistry;

	@RequestMapping(value="/launch", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void launch(@RequestParam String job, HttpServletRequest request) throws Exception {
		JobParametersBuilder builder = extractParameters(request);
		jobLauncher.run(jobRegistry.getJob(request.getParameter(JOB_PARAM_NAME)), builder.toJobParameters());
	}

	private JobParametersBuilder extractParameters(HttpServletRequest request) {
		JobParametersBuilder builder = new JobParametersBuilder();
		Enumeration<String> paramNames = request.getParameterNames();
		
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			if (!JOB_PARAM_NAME.equals(paramName)) {
				builder.addString(paramName, request.getParameter(paramName));
			}
		}
		return builder;
	}
}