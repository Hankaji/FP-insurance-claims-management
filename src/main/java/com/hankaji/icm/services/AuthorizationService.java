package com.hankaji.icm.services;

import com.hankaji.icm.lib.UserSession;
import com.hankaji.icm.models.User;

public class AuthorizationService {
    public static boolean hasRoles(User.Roles... roles) {
        try {
            User user = UserSession.getInstance().getUser();
            for (User.Roles role : roles) {
                if (user.getRole() == role) {
                    return true;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }
}
