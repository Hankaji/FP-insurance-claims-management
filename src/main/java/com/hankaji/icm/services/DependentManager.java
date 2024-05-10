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
import com.hankaji.icm.claim.Claim;
import com.hankaji.icm.customer.Dependent;
import com.hankaji.icm.lib.adapter.LocalDateTimeAdapter;
import com.hankaji.icm.system.CRUD;
import com.hankaji.icm.system.DataManager;

/**
 * The DependentManager class is responsible for managing operations related to Dependent objects.
 */
public class DependentManager extends DataManager<Dependent> implements CRUD<Dependent> {

    private static DependentManager instance;

    /**
     * Constructs a new DependentManager object.
     * It initializes the superclass with the Dependent class and a TypeToken for a Set of Dependent objects.
     */
    public DependentManager() {
        super(Dependent.class, new TypeToken<Set<Dependent>>(){});
    }

    /**
     * Returns the singleton instance of the DependentManager class.
     * If the instance is null, it creates a new instance and returns it.
     * 
     * @return The singleton instance of the DependentManager class.
     */
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
    public void add(Dependent dependent) {
        data.add(dependent);
    }

    @Override
    public void update(Dependent dependent) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Dependent dependent) {
        data.remove(dependent);
    }

    @Override
    public Claim searchById(String id) {
        return null;
    }

}
