package com.javaclasses.todolistapp;

/**
 * Enum of error types and corresponding messages
 */
public enum ErrorType {

    DUPLICATE_EMAIL("Specified email is not available"),
    INVALID_EMAIL("Specified email is not valid"),

    PASSWORD_IS_EMPTY("Password should not be empty"),
    PASSWORDS_DO_NOT_MATCH("Passwords do not match"),

    AUTHENTICATION_FAILED("Specified combination of email and password was not found"),

    TASK_DESCRIPTION_IS_EMPTY("Task description should not be empty");

    private final String message;

    ErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
