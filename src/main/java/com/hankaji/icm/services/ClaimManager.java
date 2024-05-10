package com.hankaji.icm.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hankaji.icm.claim.Claim;
import com.hankaji.icm.lib.adapter.LocalDateTimeAdapter;
import com.hankaji.icm.system.CRUD;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

public class ClaimManager implements CRUD<Claim> {

    private static ClaimManager instance;
    private static final String JSON_FILE_PATH = "data/default/Claim.json";
    private Set<Claim> data;
    private Gson gson;

    private ClaimManager() {
        data = new HashSet<>();
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        loadData();
    }

    public static ClaimManager getInstance() {
        if (instance == null) {
            instance = new ClaimManager();
        }
        return instance;
    }

    @Override
    public void loadData() {
        try {
            String content = Files.readString(Paths.get(JSON_FILE_PATH));
            Set<Claim> claims = gson.fromJson(content, new TypeToken<Set<Claim>>() {}.getType());
            if (claims != null) {
                data.addAll(claims);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Claim> getById(String id) {
        return data.stream().filter(claim -> claim.getId().equals(id)).findFirst();
    }

    @Override
    public Collection<Claim> getAll() {
        return data;
    }

    @Override
    public void add(Claim claim) {
        data.add(claim);
        saveDataToJson();
    }

    @Override
    public void update(Claim claim) {
        delete(claim);
        add(claim);
    }

    @Override
    public void delete(Claim claim) {
        data.remove(claim);
        saveDataToJson();
    }

    @Override
    public Claim searchById(String id) {
        for (Claim claim : data) {
            if (claim.getId().equals(id)) {
                return claim;
            }
        }
        return null;
    }

    private void saveDataToJson() {
        String json = gson.toJson(data);
        try {
            Files.writeString(Paths.get(JSON_FILE_PATH), json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}