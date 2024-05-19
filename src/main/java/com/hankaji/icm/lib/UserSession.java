package com.hankaji.icm.lib;

import java.util.UUID;

public class UserSession {
    private static UserSession instance;
    private UUID userId;

    private UserSession(UUID userId) {
        this.userId = userId;
    }

    public static void createSession(UUID userId) {
        if (instance == null) {
            instance = new UserSession(userId);
        }
    }

    public static UserSession getInstance() {
        return instance;
    }

    public UUID getUserId() {
        return userId;
    }

    public static void clearSession() {
        instance = null;
    }
}
