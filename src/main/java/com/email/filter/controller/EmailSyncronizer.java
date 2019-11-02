package com.email.filter.controller;

import com.email.filter.misc.Response;
import com.email.filter.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author
 */
@RequestMapping("/syncronyzer")
@Controller
public class EmailSyncronizer {

    @Autowired
    private MailService mailService;

    @RequestMapping("/load")
    @ResponseBody
    private Response syncronyze() throws Exception {
//        mailService.processUnreadEmails();
        return Response.ok();
    }
}
