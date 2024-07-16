package com.management.task.exceptionadvice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        List<String> errors =
                ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).toList();
        Error response = new Error(errors.toString());
        log.trace("Constraints Violated {}", request);
        return new ResponseEntity<>(response.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        CustomApiErrorResponse apiError = getCustomApiErrorResponse(ex, (ServletWebRequest) request);
        return handleExceptionInternal(ex, apiError, headers, request);
    }

    private CustomApiErrorResponse getCustomApiErrorResponse(BindException ex, ServletWebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getDefaultMessage());
        }

        return new CustomApiErrorResponse(HttpStatus.BAD_REQUEST,
                "Bad Request",
                String.join(". ", errors),
                request.getRequest().getRequestURI());
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, CustomApiErrorResponse apiError, HttpHeaders headers, WebRequest request) {
        return handleExceptionInternal(ex, apiError, headers, apiError.httpStatus(), request);
    }

    // For example if ENUM value is not provided correctly in request
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var customApiErrorResponse = new CustomApiErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.getMessage(), ((ServletWebRequest) request).getRequest().getRequestURI());
        return new ResponseEntity<>(customApiErrorResponse, HttpStatus.BAD_REQUEST);
    }

}
