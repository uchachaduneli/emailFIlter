package com.email.filter.Jobs;

import com.email.filter.service.MailService;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import java.util.Date;

public class Syncroniser implements Job {

    Logger logger = Logger.getLogger(Syncroniser.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            ApplicationContext applicationContext = (ApplicationContext) jobExecutionContext
                    .getScheduler().getContext().get("applicationContext");

            MailService ms = applicationContext.getBean(MailService.class);
            ms.loadEmails();
            System.out.println("Syncronizer Job executed at " + new Date());
        } catch (Exception ex) {
            logger.error("Syncronizer Method Failed", ex);
        }
    }

}
