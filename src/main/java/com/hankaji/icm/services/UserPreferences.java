package com.hankaji.icm.services;

import java.util.UUID;
import java.util.prefs.Preferences;

public class UserPreferences {
    private static final String PREFS_NAME = "LocalUserSession";
    private static final String USER_ID_KEY = "user_id";
    private Preferences prefs;

    public UserPreferences() {
        prefs = Preferences.userRoot().node(PREFS_NAME);
    }

    public void saveUserId(String userId) {
        prefs.put(USER_ID_KEY, userId);
    }

    public UUID getUserId() {
        final String userId = prefs.get(USER_ID_KEY, null); // Return null if no user ID is stored
        return userId == null ? null : UUID.fromString(userId);
    }

    public void clearUserId() {
        prefs.remove(USER_ID_KEY);
    }

}
