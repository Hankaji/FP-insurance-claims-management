package com.hankaji.icm.services;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hankaji.icm.customer.Dependent;
import com.hankaji.icm.lib.adapter.LocalDateTimeAdapter;
import com.hankaji.icm.system.CRUD;
import com.hankaji.icm.system.DataManager;

import static com.hankaji.icm.lib.Utils.isIDExisted;

public class DependentManager extends DataManager<Dependent> implements CRUD<Dependent> {

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
    public Gson useGson() {
        return new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    }

    @Override
    public Optional<Dependent> getById(String id) {
        return data.stream().filter(dependent -> dependent.getId().equals(id)).findFirst();
    }

    @Override
    public Collection<Dependent> getAll() {
        return data;
    }

    @Override
    public void add(Dependent Dependent) {
        data.add(Dependent);
    }

    @Override
    public void update(Dependent Dependent) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Dependent Dependent) {
        data.remove(Dependent);
    }

    
}
