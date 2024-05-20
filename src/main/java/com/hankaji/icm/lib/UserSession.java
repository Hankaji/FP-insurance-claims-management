package com.hankaji.icm.lib;

import com.hankaji.icm.models.User;

public class UserSession {
    private static UserSession instance;
    private User userId;

    private UserSession(User userId) {
        this.userId = userId;
    }

    public static void createSession(User userId) {
        if (instance == null) {
            instance = new UserSession(userId);
        }
    }

    public static UserSession getInstance() {
        return instance;
    }

    public User getUserId() {
        return userId;
    }

    public static void clearSession() {
        instance = null;
    }
}
