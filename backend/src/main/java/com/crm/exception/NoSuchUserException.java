package com.crm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchUserException extends RuntimeException{
    public NoSuchUserException(String message) { super(message);}
}

