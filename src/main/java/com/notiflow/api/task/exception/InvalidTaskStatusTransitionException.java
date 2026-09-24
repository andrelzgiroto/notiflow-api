package com.notiflow.api.task.exception;

public class InvalidTaskStatusTransitionException extends RuntimeException {
    public InvalidTaskStatusTransitionException(String message) {
        super(message);
    }
}
