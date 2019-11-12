package com.email.filter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author
 */
@Controller
@RequestMapping
public class WebController {

    @RequestMapping("/users")
    public String users() {
        return "users";
    }

    @RequestMapping("/filters")
    public String filters() {
        return "filters";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/emails")
    public String emails() {
        return "emails";
    }

    @RequestMapping("/spam")
    public String spam() {
        return "spam";
    }

    @RequestMapping("/")
    public String defaultFnc() {
        return "";
    }

}
