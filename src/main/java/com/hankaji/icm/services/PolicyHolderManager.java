package com.hankaji.icm.services;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hankaji.icm.customer.PolicyHolder;
import com.hankaji.icm.lib.adapter.LocalDateTimeAdapter;
import com.hankaji.icm.system.CRUD;
import com.hankaji.icm.system.DataManager;

public class PolicyHolderManager extends DataManager<PolicyHolder> implements CRUD<PolicyHolder> {

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
    public Gson useGson() {
        return new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    }

    @Override
    public Optional<PolicyHolder> getById(String id) {
        return data.stream().filter(policyHolder -> policyHolder.getId().equals(id)).findFirst();
    }

    @Override
    public Collection<PolicyHolder> getAll() {
        return data;
    }

    @Override
    public void add(PolicyHolder PolicyHolder) {
        data.add(PolicyHolder);
    }

    @Override
    public void update(PolicyHolder PolicyHolder) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(PolicyHolder PolicyHolder) {
        data.remove(PolicyHolder);
    }
    
}
