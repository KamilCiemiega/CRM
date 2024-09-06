package com.crm.exception;

public class NoSuchFolderException extends RuntimeException{

    public NoSuchFolderException(String message) {
        super(message);
    }

    public NoSuchFolderException(String message, Throwable cause) {
        super(message, cause);
    }
}
