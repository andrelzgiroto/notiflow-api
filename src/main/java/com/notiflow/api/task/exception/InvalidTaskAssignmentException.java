package com.notiflow.api.task.exception;

public class InvalidTaskAssignmentException extends RuntimeException {
    public InvalidTaskAssignmentException(String message) {
        super(message);
    }
}
