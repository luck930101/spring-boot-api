package com.yuboz.springbootapi.handler;

import com.yuboz.springbootapi.resource.InvalidErrorResource;
import com.yuboz.springbootapi.exception.InvalidRequestException;
import com.yuboz.springbootapi.exception.NotFoundException;
import com.yuboz.springbootapi.resource.ErrorResource;
import com.yuboz.springbootapi.resource.FieldResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<?> handleNotFound(RuntimeException e){

        ErrorResource errorResource = new ErrorResource(e.getMessage());
        ResponseEntity result = new ResponseEntity<Object>(errorResource, HttpStatus.NOT_FOUND);
        logger.info("Request -----------{}",result);
        return result;
    }

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseBody
    public ResponseEntity<?> handleInvalidRequest(InvalidRequestException e){
        Errors errors = e.getErrors();
        List<FieldResource> fieldResources = new ArrayList<>();
        List<FieldError> fieldErrors = errors.getFieldErrors();


        for (FieldError fieldError : fieldErrors){
            FieldResource fieldResource = new FieldResource(fieldError.getObjectName(),
                    fieldError.getField(),
                    fieldError.getCode(),
                    fieldError.getDefaultMessage()
            );

            fieldResources.add(fieldResource);
        }
        InvalidErrorResource ier = new InvalidErrorResource(e.getMessage(),fieldResources);
        ResponseEntity result = new ResponseEntity<Object>(ier, HttpStatus.BAD_REQUEST);
        logger.info("Request -----------{}",result);

        return result;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> handleException(Exception e){

        logger.error("Error ----{}",e.getMessage());
        return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
