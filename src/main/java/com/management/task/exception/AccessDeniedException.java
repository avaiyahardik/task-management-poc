package com.management.task.exception;

import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@StandardException
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class AccessDeniedException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public AccessDeniedException() {
        super("Access Denied");
    }

}
