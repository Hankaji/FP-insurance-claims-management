package com.hankaji.icm.services;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hankaji.icm.customer.Dependent;
import com.hankaji.icm.lib.adapter.LocalDateTimeAdapter;
import com.hankaji.icm.system.DataManager;

import static com.hankaji.icm.lib.Utils.isIDExisted;

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
        for (Dependent dependent : data) {
            if (dependent.getId().equals(id)) {
                return dependent;
            }
        }
        return null;
    }

    @Override
    public void add(Dependent t) {
        // Extract all data's id into a set
        Set<String> ids = getAll().stream().map(Dependent::getId).collect(Collectors.toSet());

        // Check if the id is existed, if not, add the data
        if (!isIDExisted(t.getId(), ids)) data.add(t);
    }

    @Override
    public boolean update(Dependent t) {
        for (Dependent dependent : data) {
            if (dependent.getId().equals(t.getId())) {
                dependent.setInsuranceCard(t.getInsuranceCard());
                dependent.setClaims(t.getClaims());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        for (Dependent dependent : data) {
            if (dependent.getId().equals(id)) {
                data.remove(dependent);
                return true;
            }
        }
        return false;
    }

    @Override
    public Gson createGson() {
        return new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    }
}
