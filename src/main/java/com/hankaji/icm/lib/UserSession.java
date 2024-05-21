package com.hankaji.icm.lib;

import com.hankaji.icm.errors.UserInstanceNotExistedException;
import com.hankaji.icm.models.User;

public class UserSession {
    private static UserSession instance;
    private User user;

    private UserSession(User userId) {
        this.user = userId;
    }

    public static void createSession(User userId) {
        if (instance == null) {
            instance = new UserSession(userId);
        } else {
            instance.updateUser(userId);
        }
    }

    public static UserSession getInstance() throws UserInstanceNotExistedException {
        if (instance == null) {
            throw new UserInstanceNotExistedException();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public static void clearSession() {
        instance = null;
    }

    private void updateUser(User user) {
        this.user = user;
    }
}
