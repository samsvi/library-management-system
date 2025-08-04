package com.library.librarymanagement.exception;

import java.util.List;

public class UpdateValidationException extends RuntimeException{

    private final List<String> errors;

    public UpdateValidationException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }

    public UpdateValidationException(String message, Throwable cause, List<String> errors) {
        super(message, cause);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
