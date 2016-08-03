package com.javaclasses.todolistapp;

/**
 * Exception that indicates authentication failure
 */
public class AuthenticationException extends TodoListAppException {

    public AuthenticationException(ErrorType errorType) {
        super(errorType);
    }
}
