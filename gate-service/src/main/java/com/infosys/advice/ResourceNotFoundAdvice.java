package com.infosys.advice;

import com.amazonaws.services.rekognition.model.InternalServerErrorException;
import com.amazonaws.services.rekognition.model.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ResourceNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String internalServerErrorHandler(ResourceNotFoundException ex) {
        return ex.getMessage();
    }

}
