package com.email.filter.controller;

import com.email.filter.misc.Response;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response handleException(HttpServletRequest request, Exception ex) {
        logger.error("{{{ Exception Occured, URL=" + request.getRequestURL() + "\t" + ExceptionUtils.getRootCauseMessage(ex) + "  }}}", ex);
        return Response.withErrorData("Error Occured: " + ExceptionUtils.getRootCause(ex).getLocalizedMessage(), ExceptionUtils.getRootCauseMessage(ex));
    }

}
