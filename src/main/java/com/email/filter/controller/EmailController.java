package com.email.filter.controller;

import com.email.filter.misc.Response;
import com.email.filter.request.MailRequest;
import com.email.filter.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * @author
 */
@RequestMapping("/emails")
@Controller
public class EmailController {

    @Autowired
    private MailService mailService;

    @RequestMapping("/get-emails")
    @ResponseBody
    private Response getEmails(@RequestParam("start") int start, @RequestParam("limit") int limit,
                               @RequestBody MailRequest request, HttpServletRequest servletRequest) throws Exception {
        return Response.withSuccess(mailService.getEmails(start, limit, request));
    }

    @RequestMapping("/get-email-folders")
    @ResponseBody
    private Response getEmailFolders() throws Exception {
        return Response.withSuccess(mailService.getEmailFolders());
    }


}
