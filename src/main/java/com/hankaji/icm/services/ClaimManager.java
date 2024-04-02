package com.hankaji.icm.services;

import java.time.LocalDateTime;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hankaji.icm.claim.Claim;
import com.hankaji.icm.lib.adapter.LocalDateTimeAdapter;
import com.hankaji.icm.system.DataManager;

public class ClaimManager extends DataManager<Claim> {

    private static ClaimManager instance;

    public ClaimManager() {
        super(Claim.class, new TypeToken<Set<Claim>>(){});
    }

    public static ClaimManager getInstance() {
        if (instance == null) {
            instance = new ClaimManager();
        }
        return instance;
    }

    @Override
    public Claim get(String id) {
        for (Claim claim : data) {
            if (claim.getId().equals(id)) {
                return claim;
            }
        }
        return null;
    }

    @Override
    public void add(Claim t) {
        data.add(t);
    }

    @Override
    public boolean update(Claim t) {
        for (Claim claim : data) {
            if (claim.getId().equals(t.getId())) {
                data.remove(claim);
                return data.add(t);
            }
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        for (Claim claim : data) {
            if (claim.getId().equals(id)) {
                data.remove(claim);
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
