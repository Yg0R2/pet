package com.yg0r2.pet.web.rest;

import com.yg0r2.pet.service.exceptions.PetEntryNotFoundException;
import com.yg0r2.pet.service.exceptions.PetServiceInternalException;
import com.yg0r2.pet.service.exceptions.UnableToCreatePetEntryException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseStatusExceptionResolver {

    @ExceptionHandler(value = PetEntryNotFoundException.class)
    public ResponseEntity<String> handleNotFoundExceptions(RuntimeException runtimeException) {
        return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UnableToCreatePetEntryException.class)
    public ResponseEntity<String> handleCreateExceptions(RuntimeException runtimeException) {
        return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = PetServiceInternalException.class)
    public ResponseEntity<String> handleInternalException(RuntimeException runtimeException) {
        return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException runtimeException) {
        return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
