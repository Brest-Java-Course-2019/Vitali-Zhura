package com.epam.courses.paycom.web_app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;


@ControllerAdvice
public class ErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);

    @ExceptionHandler(HttpServerErrorException.class)

    public final String handleAnyException(
            final HttpServerErrorException e, final Model model) {
        LOGGER.debug("handleAnyException({}, {})", e, model);
        model.addAttribute("message", e.getMessage());
        return "error";
    }
}