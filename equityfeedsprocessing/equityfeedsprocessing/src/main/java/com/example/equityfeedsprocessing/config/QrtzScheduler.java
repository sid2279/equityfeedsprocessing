package com.example.equityfeedsprocessing.config;

import java.util.Calendar;

import com.example.equityfeedsprocessing.quartz.SampleJob;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

@Configuration
public class QrtzScheduler {
	
	@Bean
	public SpringBeanJobFactory springBeanJobFactory(ApplicationContext applicationContext) {
		AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;		
	}
	
	@Bean 
	public SchedulerFactoryBean scheduler(Trigger trigger, JobDetail job, ApplicationContext applicationContext) {

		SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
		schedulerFactory.setConfigLocation(new ClassPathResource("quartz.properties"));
		schedulerFactory.setJobFactory(springBeanJobFactory(applicationContext));
	    schedulerFactory.setJobDetails(job);
	    schedulerFactory.setTriggers(trigger);
	    schedulerFactory.setOverwriteExistingJobs(true);
	    schedulerFactory.setAutoStartup(true);
	    schedulerFactory.setWaitForJobsToCompleteOnShutdown(true);
	    return schedulerFactory;
	}

	@Bean(name = "memberStats")
	public JobDetail jobDetail() {
	    return JobBuilder.newJob().ofType(SampleJob.class)
	      .storeDurably()
	      .withIdentity("Qrtz_Job_Detail")  
	      .withDescription("Invoke Sample Job service...")
	      .build();
	}
	
    static CronTriggerFactoryBean createCronTrigger(JobDetail jobDetail, String cronExpression, String triggerName) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setCronExpression(cronExpression);
        factoryBean.setStartTime(calendar.getTime());
        factoryBean.setStartDelay(0L);
        factoryBean.setName(triggerName);
        factoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);

        return factoryBean;

    }
	
}
