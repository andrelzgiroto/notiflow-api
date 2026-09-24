package com.notiflow.api.user.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("There is already a registered user with the email address: " + email);
    }
}
