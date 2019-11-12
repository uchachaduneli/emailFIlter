package com.email.filter.Jobs;

import com.email.filter.service.MailService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Syncroniser {

    Logger logger = Logger.getLogger(Syncroniser.class);

    @Autowired
    MailService mailService;

    @Scheduled(fixedRate = 30000)
    public void runTask() {
        logger.debug("************ Syncronizer Started ************");
        try {
            mailService.loadEmails();
        } catch (Exception e) {
            logger.error("************ Mail Syncronizatino Failed *************", e);
        }
        logger.info("************ Syncronizer Ended ************");
    }
}
