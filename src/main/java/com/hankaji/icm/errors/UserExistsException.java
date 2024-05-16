package com.hankaji.icm.errors;

public class UserExistsException extends Exception {
    public UserExistsException() {
        super("User already exists");
    }
}
