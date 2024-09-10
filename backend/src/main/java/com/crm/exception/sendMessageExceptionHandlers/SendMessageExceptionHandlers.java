package com.crm.exception.sendMessageExceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class SendMessageExceptionHandlers {

    @ResponseStatus(HttpStatus.CONFLICT)
    public static class  DuplicateFolderException extends RuntimeException {
        public DuplicateFolderException(String message) {
            super(message);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class NoSuchFolderException extends RuntimeException{
        public NoSuchFolderException(String message) {
            super(message);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class NoSuchUserException extends RuntimeException {

        public NoSuchUserException(String message) {
            super(message);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class NoSuchMessageException extends RuntimeException {
        public NoSuchMessageException(String message) {
            super(message);
        }
    }
}
