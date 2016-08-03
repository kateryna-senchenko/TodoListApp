package com.javaclasses.todolistapp;

/**
 * Exception that indicates registration failure
 */
public class RegistrationException extends TodoListAppException {

    public RegistrationException(ErrorType errorType) {
        super(errorType);
    }
}
