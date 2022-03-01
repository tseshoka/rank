package com.rank.interactive.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
