package com.hankaji.icm.services;

import static com.hankaji.icm.lib.Utils.isIDExisted;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hankaji.icm.customer.PolicyHolder;
import com.hankaji.icm.lib.adapter.LocalDateTimeAdapter;
import com.hankaji.icm.system.DataManager;

public class PolicyHolderManager extends DataManager<PolicyHolder> {

    private static PolicyHolderManager instance;

    public PolicyHolderManager() {
        super(PolicyHolder.class, new TypeToken<Set<PolicyHolder>>(){});
    }

    public static PolicyHolderManager getInstance() {
        if (instance == null) {
            instance = new PolicyHolderManager();
        }
        return instance;
    }

    @Override
    public PolicyHolder get(String id) {
        for (PolicyHolder dependent : data) {
            if (dependent.getId().equals(id)) {
                return dependent;
            }
        }
        return null;
    }

    @Override
    public void add(PolicyHolder t) {
        // Extract all data's id into a set
        Set<String> ids = getAll().stream().map(PolicyHolder::getId).collect(Collectors.toSet());

        // Check if the id is existed, if not, add the data
        if (!isIDExisted(t.getId(), ids)) data.add(t);
    }

    @Override
    public boolean update(PolicyHolder t) {
        for (PolicyHolder dependent : data) {
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
        for (PolicyHolder dependent : data) {
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
