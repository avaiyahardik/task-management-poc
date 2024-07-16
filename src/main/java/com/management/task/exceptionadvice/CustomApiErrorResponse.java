package com.management.task.exceptionadvice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record CustomApiErrorResponse(
    @JsonIgnore
    HttpStatus httpStatus,
    String message,
    ZonedDateTime timestamp,
    String error,
    String path
) {
    public CustomApiErrorResponse(HttpStatus httpStatus, String error, String message, String path) {
        this(httpStatus, message, ZonedDateTime.now(), error, path);
    }
}
