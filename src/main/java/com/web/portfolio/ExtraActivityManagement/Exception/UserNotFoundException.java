package com.web.portfolio.ExtraActivityManagement.Exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String userId, String additionalContext) {
        super(String.format("User with ID '%s' not found. Context: %s", userId, additionalContext));
    }
}