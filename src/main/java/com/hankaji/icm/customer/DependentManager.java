package com.hankaji.icm.customer;

import java.util.Set;

import com.google.gson.reflect.TypeToken;
import com.hankaji.icm.system.DataManager;

public class DependentManager extends DataManager<Dependent> {

    private static DependentManager instance;

    public DependentManager() {
        super(Dependent.class, new TypeToken<Set<Dependent>>(){});
    }

    public static DependentManager getInstance() {
        if (instance == null) {
            instance = new DependentManager();
        }
        return instance;
    }

    @Override
    public Dependent get(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public void add(Dependent t) {
        data.add(t);
    }

    @Override
    public boolean update(Dependent t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
    }
}
