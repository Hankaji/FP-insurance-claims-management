package com.hankaji.icm.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hankaji.icm.claim.Claim;
import com.hankaji.icm.lib.adapter.LocalDateTimeAdapter;
import com.hankaji.icm.system.CRUD;
import com.hankaji.icm.system.DataManager;

public class ClaimManager extends DataManager<Claim> implements CRUD<Claim> {

    private static ClaimManager instance;

    public ClaimManager() {
        super(Claim.class, new TypeToken<Set<Claim>>() {
        });
    }

    public static ClaimManager getInstance() {
        if (instance == null) {
            instance = new ClaimManager();
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
    }

    /**
     * Updates the specified claim with the given parameters.
     * 
     * @param claim the claim to update
     * @param params required parameters for updating the claim
     */
    @Override
    public void update(Claim claim) {
        if (data.contains(claim)) {
            Optional<Claim> updateClaim = getById(claim.getId());
            if (updateClaim.isPresent()) {
                Claim updatedClaim = updateClaim.get();
                updatedClaim.setExamDate(claim.getExamDate());
                updatedClaim.setDocuments(claim.getDocuments());
                updatedClaim.setClaimAmount(claim.getClaimAmount());
                updatedClaim.setStatus(claim.getStatus());
                updatedClaim.setReceiverBankingInfo(claim.getReceiverBankingInfo());
            }
        }
    }

    @Override
    public void delete(Claim claim) {
        data.remove(claim);
    }

}
