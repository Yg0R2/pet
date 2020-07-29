package com.yg0r2.pet.web.rest;

import com.yg0r2.pet.service.exceptions.PetEntryNotFoundException;
import com.yg0r2.pet.service.exceptions.PetServiceInternalException;
import com.yg0r2.pet.service.exceptions.UnableToCreatePetEntryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseStatusExceptionResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @ExceptionHandler(value = {
        EmptyResultDataAccessException.class,
        HttpMediaTypeNotSupportedException.class,
        PetEntryNotFoundException.class,
        RuntimeException.class,
        UnableToCreatePetEntryException.class
    })
    public ResponseEntity<String> handleNotFoundExceptions(Exception exception) {
        LOGGER.error(exception.getMessage(), exception);

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {PetServiceInternalException.class, Exception.class})
    public ResponseEntity<String> handleInternalException(Exception exception) {
        LOGGER.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), exception);

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
