package com.example.agregator.exceptions;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(final ConstraintViolationException e) throws UnsupportedEncodingException {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse();
        log.error("{} {}", httpStatus.value(), e.getMessage());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(out, true, "UTF-8"));
        String stackTrace = out.toString(Charset.forName("UTF-8"));
        return new ErrorResponse(e.getMessage(), stackTrace);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(final MissingServletRequestParameterException e) throws UnsupportedEncodingException {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse();
        log.error("{} {}", httpStatus.value(), e.getMessage());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(out, true, "UTF-8"));
        String stackTrace = out.toString(Charset.forName("UTF-8"));
        return new ErrorResponse(e.getMessage(), stackTrace);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(final ValidationException e) throws UnsupportedEncodingException {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST; // 400
        ErrorResponse errorResponse = new ErrorResponse();
        log.error("{} {}", httpStatus.value(), e.getMessage());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(out, true, "UTF-8"));
        String stackTrace = out.toString(Charset.forName("UTF-8"));
        return new ErrorResponse(e.getMessage(), stackTrace);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(final RequestException e) throws UnsupportedEncodingException {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR; // 500
        ErrorResponse errorResponse = new ErrorResponse();
        log.error("{} {}", httpStatus.value(), e.getMessage());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(out, true, "UTF-8"));
        String stackTrace = out.toString(Charset.forName("UTF-8"));
        return new ErrorResponse(e.getMessage(), stackTrace);
    }
}
