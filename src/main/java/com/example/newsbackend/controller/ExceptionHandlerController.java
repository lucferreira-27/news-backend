package com.example.newsbackend.controller;

import com.example.newsbackend.controller.dtos.ErrorMessageDto;
import com.example.newsbackend.exception.BadRequestException;
import com.example.newsbackend.exception.ResourceAlreadyExistsException;
import com.example.newsbackend.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.stream.Collectors;

@EnableWebMvc
@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<ErrorMessageDto> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorMessageDto message = new ErrorMessageDto(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false).replace("uri=",""));

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ResourceAlreadyExistsException.class})
    public ResponseEntity<ErrorMessageDto> resourceAlreadyExistsException(ResourceAlreadyExistsException ex, WebRequest request) {
        ErrorMessageDto message = new ErrorMessageDto(
                HttpStatus.CONFLICT.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false).replace("uri=",""));

        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<ErrorMessageDto> badRequestException(BadRequestException ex, WebRequest request) {
        ErrorMessageDto message = new ErrorMessageDto(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false).replace("uri=",""));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<ErrorMessageDto> badRequestException(ConstraintViolationException ex, WebRequest request) {
        String msg = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getMessage())
                .collect(Collectors.joining(","));

        ErrorMessageDto message = new ErrorMessageDto(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                msg,
                request.getDescription(false).replace("uri=",""));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorMessageDto> routeNotFoundException(WebRequest request) {
        ErrorMessageDto message = new ErrorMessageDto(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                "Route not found",
                request.getDescription(false).replace("uri=",""));

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }


}
