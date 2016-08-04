package com.javaclasses.todolistapp;

/**
 * Class for app exceptions
 */
/*package*/ class TodoListAppException extends Exception{

    private final ErrorType errorType;

    /*package*/ TodoListAppException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    /*package*/ ErrorType getErrorType() {
        return errorType;
    }
}
