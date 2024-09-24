package com.crm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchMessageException extends RuntimeException{
    public NoSuchMessageException(String message) {
        super(message);
    }
}
