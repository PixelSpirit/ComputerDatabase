package com.excilys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionHandlingController {

    private Logger logger = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handlePageNotFoundException(NoHandlerFoundException ex) {
        logger.error("[HTTP Error] PAGE NOT FOUND EXCEPTION ! (404)");
        return "errors/404";
    }

    @ExceptionHandler(Exception.class)
    public String handleIllegalArgumentException(Exception ex) {
        logger.error("[HTTP Error] INTERNAL ERROR ! (500)");
        ex.printStackTrace();
        return "errors/500";
    }

}
