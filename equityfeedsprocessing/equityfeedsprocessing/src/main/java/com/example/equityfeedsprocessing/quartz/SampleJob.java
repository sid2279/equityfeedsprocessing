package com.example.equityfeedsprocessing.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SampleJob implements Job {
	
	@Autowired
	private SampleJobService sampleJobService;

	private static final Logger logger = LoggerFactory.getLogger(SampleJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		logger.info("In execute method of Sample Job.");

		logger.info("Calling the executeSampleJob() method of the sampleJobService class.");

		logger.info(" START | Sample Job executed.");

		sampleJobService.executeSampleJob();

		logger.info(" END | Sample Job executed.");
	}	

}
