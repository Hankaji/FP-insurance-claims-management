package com.hankaji.icm.controllers;

import com.hankaji.icm.database.SessionManager;
import com.hankaji.icm.lib.UserSession;
import com.hankaji.icm.models.User;
import com.hankaji.icm.models.Provider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class ViewSurveyorsController {
    private SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();

    public ViewSurveyorsController() {
    }

    public List<Provider> getSurveyors() {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, UserSession.getInstance().getUserId());

            if (user != null && user.getRole() == User.Roles.PROVIDER) {
                // From current user get Provider
                String hql = "FROM Provider P WHERE P.user.id = :user_id";
                Query<Provider> query = session.createQuery(hql, Provider.class);
                query.setParameter("user_id", user.getId());
                Provider provider = query.uniqueResult();

                if (provider != null && provider.getSurveyors() != null) {
                    return provider.getSurveyors();
                }
            }
            return List.of();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
