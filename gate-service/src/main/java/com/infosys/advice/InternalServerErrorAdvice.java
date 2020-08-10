package com.infosys.advice;

import com.amazonaws.services.rekognition.model.InternalServerErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InternalServerErrorAdvice {
    @ResponseBody
    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String internalServerErrorHandler(InternalServerErrorException ex) {
        return ex.getMessage();
    }
}
