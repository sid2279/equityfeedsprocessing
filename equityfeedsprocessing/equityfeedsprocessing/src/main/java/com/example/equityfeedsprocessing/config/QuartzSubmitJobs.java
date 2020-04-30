package com.example.equityfeedsprocessing.config;

import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;

@Configuration
public class QuartzSubmitJobs {
	
    private static final String CRON_EVERY_WEEKDAY = "0 28 11 ? * MON-FRI";

    @Bean(name = "memberStatsTrigger")
    public CronTriggerFactoryBean triggerMemberClassStats(@Qualifier("memberStats") JobDetail jobDetail) {
        return QrtzScheduler.createCronTrigger(jobDetail, CRON_EVERY_WEEKDAY, "Sample Job Trigger");
    }

}
