package com.notiflow.api.task.exception;

import com.notiflow.api.user.exception.EmailAlreadyExistsException;
import com.notiflow.api.user.exception.UserNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class TaskExceptionHandler {

    @ExceptionHandler(InvalidTaskStatusTransitionException.class)
    public ProblemDetail handleInvalidTaskStatusTransitionException(InvalidTaskStatusTransitionException ex) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                ex.getMessage()
        );
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ProblemDetail handleTaskNotFoundException(TaskNotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
    }

    @ExceptionHandler(InvalidTaskAssignmentException.class)
    public ProblemDetail handleInvalidTaskAssignmentException(InvalidTaskAssignmentException ex) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN,
                ex.getMessage()
        );
    }
}