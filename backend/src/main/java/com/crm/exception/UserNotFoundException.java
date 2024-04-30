package com.crm.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {
        super("Couldn't find email like: " + email);
    }
}
