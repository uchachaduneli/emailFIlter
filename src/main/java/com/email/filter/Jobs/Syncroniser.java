package com.email.filter.Jobs;

import com.email.filter.service.MailService;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class Syncroniser implements Job {

    Logger log = Logger.getLogger(Syncroniser.class);

    @Autowired
    MailService mailService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Job executed at " + new Date());
    }

}
