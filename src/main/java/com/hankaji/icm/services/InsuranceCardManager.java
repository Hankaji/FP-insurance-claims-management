package com.hankaji.icm.services;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hankaji.icm.card.InsuranceCard;
import com.hankaji.icm.lib.adapter.LocalDateTimeAdapter;
import com.hankaji.icm.system.DataManager;

import static com.hankaji.icm.lib.Utils.isIDExisted;

public class InsuranceCardManager extends DataManager<InsuranceCard> {

    private static InsuranceCardManager instance;

    public InsuranceCardManager() {
        super(InsuranceCard.class, new TypeToken<Set<InsuranceCard>>(){});
    }

    public static InsuranceCardManager getInstance() {
        if (instance == null) {
            instance = new InsuranceCardManager();
        }
        return instance;
    }

    @Override
    public InsuranceCard get(String id) {
        for (InsuranceCard card : data) {
            if (card.getCardNumber().equals(id)) {
                return card;
            }
        }
        return null;
    }

    @Override
    public void add(InsuranceCard t) {
        // Extract all data's id into a set
        Set<String> ids = getAll().stream().map(InsuranceCard::getCardNumber).collect(Collectors.toSet());

        // Check if the id is existed, if not, add the data
        if (!isIDExisted(t.getCardNumber(), ids)) data.add(t);
    }

    @Override
    public boolean update(InsuranceCard t) {
        // for (InsuranceCard dependent : data) {
        //     if (dependent.getCardNumber().equals(t.getCardNumber())) {
                
        //         return true;
        //     }
        // }
        return false;
    }

    @Override
    public boolean delete(String id) {
        for (InsuranceCard dependent : data) {
            if (dependent.getCardNumber().equals(id)) {
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
