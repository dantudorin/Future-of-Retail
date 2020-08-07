package com.infosys.advice;

import com.infosys.exception.FaceNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class FaceNotExistsAdvice {
    @ResponseBody
    @ExceptionHandler(FaceNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userNotFoundHandler(FaceNotExistsException e) {
        return e.getMessage();
    }
}
