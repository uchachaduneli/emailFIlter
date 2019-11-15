package com.email.filter.controller;

import com.email.filter.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author
 */
@RequestMapping("/misc")
@Controller
public class MiscController {

    @Autowired
    private FileService fileService;

    @RequestMapping("/get-file")
    @ResponseBody
    private void getImage(HttpServletResponse response, @RequestParam String name) throws IOException {
        response.getOutputStream().write(fileService.readFile(name));
    }
}
