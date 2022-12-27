package com.example.Blogger.common;

import com.example.Blogger.common.exceptions.DataValidationException;
import com.example.Blogger.common.wrapper.ResponseWrapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@ControllerAdvice(annotations = RestController.class)
public abstract class ApiRestHandler implements ApplicationEventPublisherAware {

    protected ApplicationEventPublisher eventPublisher;
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataValidationException.class)
    public @ResponseBody
    ResponseWrapper handleDataValidationException(DataValidationException ex, HttpServletRequest request, HttpServletResponse response) {
        return ResponseWrapper.errorResponse(request.getRequestURI(), ex.getMessage(), null);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }
}
