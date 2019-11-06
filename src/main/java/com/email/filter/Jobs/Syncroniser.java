package com.email.filter.Jobs;

import com.email.filter.service.MailService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class Syncroniser implements Job {

    @Autowired
    MailService mailService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //mailService.processUnreadEmails();
        System.out.println("Job executed at " + new Date());
    }

}
