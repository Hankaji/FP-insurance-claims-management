//package com.hankaji.icm.services;
//
//import com.hankaji.icm.claim.Claim;
//import com.hankaji.icm.system.CRUD;
//import com.hankaji.icm.views.HibernateUtil;
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//
//import java.util.Collection;
//import java.util.Optional;
//
//public class ClaimManager implements CRUD<Claim> {
//
//    private static ClaimManager instance;
//
//    private ClaimManager() {}
//
//    public static ClaimManager getInstance() {
//        if (instance == null) {
//            instance = new ClaimManager();
//        }
//        return instance;
//    }
//
////    @Override
////    public Optional<Claim> getById(String id) {
////        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
////            return Optional.ofNullable(session.get(Claim.class, id));
////        } catch (Exception e) {
////            e.printStackTrace();
////            return Optional.empty();
////        }
//    }
//
//    @Override
//    public Collection<Claim> getAll() {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            return session.createQuery("from Claim", Claim.class).list();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public void add(Claim claim) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            Transaction transaction = session.beginTransaction();
//            session.save(claim);
//            transaction.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void update(Claim claim) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            Transaction transaction = session.beginTransaction();
//            session.update(claim);
//            transaction.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void delete(Claim claim) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            Transaction transaction = session.beginTransaction();
//            session.delete(claim);
//            transaction.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public Claim searchById(String id) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            return session.get(Claim.class, id);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}