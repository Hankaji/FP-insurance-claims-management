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
import com.hankaji.icm.customer.PolicyHolder;
import com.hankaji.icm.lib.adapter.LocalDateTimeAdapter;
import com.hankaji.icm.system.CRUD;
import com.hankaji.icm.system.DataManager;

/**
 * The PolicyHolderManager class is responsible for managing operations related to PolicyHolder objects.
 */
public class PolicyHolderManager extends DataManager<PolicyHolder> implements CRUD<PolicyHolder> {

    private static PolicyHolderManager instance;

    /**
     * Constructs a new PolicyHolderManager object.
     * It initializes the superclass with the PolicyHolder class and a TypeToken for a Set of PolicyHolder objects.
     */
    public PolicyHolderManager() {
        super(PolicyHolder.class, new TypeToken<Set<PolicyHolder>>(){});
    }

    /**
     * Returns the singleton instance of the PolicyHolderManager class.
     * If the instance is null, it creates a new instance and returns it.
     * 
     * @return The singleton instance of the PolicyHolderManager class.
     */
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
