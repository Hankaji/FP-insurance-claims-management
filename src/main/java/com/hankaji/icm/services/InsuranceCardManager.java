package com.hankaji.icm.services;
/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: Lanterna, Gson, Apache Commons IO
*/

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hankaji.icm.card.InsuranceCard;
import com.hankaji.icm.claim.Claim;
import com.hankaji.icm.lib.adapter.LocalDateTimeAdapter;
import com.hankaji.icm.system.CRUD;
import com.hankaji.icm.system.DataManager;

/**
 * The InsuranceCardManager class is responsible for managing operations related to InsuranceCard objects.
 */
public class InsuranceCardManager extends DataManager<InsuranceCard> implements CRUD<InsuranceCard> {

    private static InsuranceCardManager instance;

    /**
     * Constructs a new InsuranceCardManager object.
     * It initializes the superclass with the InsuranceCard class and a TypeToken for a Set of InsuranceCard objects.
     */
    public InsuranceCardManager() {
        super(InsuranceCard.class, new TypeToken<Set<InsuranceCard>>(){});
    }

    /**
     * Returns the singleton instance of the InsuranceCardManager class.
     * If the instance is null, it creates a new instance and returns it.
     * 
     * @return The singleton instance of the InsuranceCardManager class.
     */
    public static InsuranceCardManager getInstance() {
        if (instance == null) {
            instance = new InsuranceCardManager();
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
    public Optional<InsuranceCard> getById(String id) {
        return data.stream().filter(insuranceCard -> insuranceCard.getCardNumber().equals(id)).findFirst();
    }

    @Override
    public Collection<InsuranceCard> getAll() {
        return data;
    }

    @Override
    public void add(InsuranceCard InsuranceCard) {
        data.add(InsuranceCard);
    }

    @Override
    public void update(InsuranceCard InsuranceCard) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(InsuranceCard InsuranceCard) {
        data.remove(InsuranceCard);
    }

    @Override
    public Claim searchById(String id) {
        return null;
    }


}
