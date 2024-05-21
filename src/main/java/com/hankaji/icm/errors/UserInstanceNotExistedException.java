package com.hankaji.icm.errors;

public class UserInstanceNotExistedException extends Exception {
    public UserInstanceNotExistedException() {
        super("User instance does not exist. Try to login again.");
    }
}
