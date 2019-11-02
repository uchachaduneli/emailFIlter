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

    @RequestMapping("/requests")
    public String requests() {
        return "requests";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/contacts")
    public String contacts() {
        return "contacts";
    }

    @RequestMapping("/emails")
    public String emails() {
        return "emails";
    }

    @RequestMapping("/hotels")
    public String hotels() {
        return "hotels";
    }

    @RequestMapping("/objects")
    public String objects() {
        return "objects";
    }

    @RequestMapping("/places")
    public String places() {
        return "places";
    }

    @RequestMapping("/transports")
    public String transports() {
        return "transports";
    }

    @RequestMapping("/nonstandarts")
    public String nonstandarts() {
        return "nonstandarts";
    }

    @RequestMapping("/product")
    public String product() {
        return "product";
    }

    @RequestMapping("/restaurants")
    public String restaurants() {
        return "restaurants";
    }

    @RequestMapping("/distances")
    public String distances() {
        return "distances";
    }

    @RequestMapping("/guides")
    public String guides() {
        return "guides";
    }

    @RequestMapping("/financial")
    public String financial() {
        return "financial";
    }

    @RequestMapping("/")
    public String defaultFnc() {
        return "";
    }

}
