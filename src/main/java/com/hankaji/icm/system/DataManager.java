package com.hankaji.icm.system;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * The DataManager class is an abstract class that provides a common structure and functionality for managing data of a specific type.
 * It includes methods for retrieving, adding, updating, and deleting data, as well as saving the data to a file.
 *
 * @param <T> the type of data managed by the DataManager
 */
/**
 * This abstract class represents a data manager that provides basic CRUD operations for managing data of a specific type.
 * It also provides functionality to load and save data from/to a file.
 *
 * @param <T> the type of data managed by this DataManager
 */
public abstract class DataManager<T> {

    protected Set<T> data;

    protected String fileName;

    private final TypeToken<Set<T>> dataType;

    /**
     * Constructs a DataManager object with the given class type.
     * The class type is used to determine the file name for data storage.
     * It also adds a shutdown hook to save data before the program exits.
     *
     * @param clazz the class type of the data managed by this DataManager
     */
    protected DataManager(Class<T> clazz, TypeToken<Set<T>> typeToken) {
        this.fileName = clazz.getSimpleName();

        // create a Type object for the data type
        this.dataType = typeToken;
        
        // Add a shutdown hook to save data before the program exits
        Runtime.getRuntime().addShutdownHook(new Thread(() -> saveData()));

        // Load data from file
        try {
            File file = new File(String.format("./data/%s.json", fileName));
            String json = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

            Gson gson = new Gson();

            this.data = gson.fromJson(json, dataType);
        } catch (NoSuchFileException e) {
            System.out.println(String.format("No data of %s found, initializing new data", fileName));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(String.format("Error loading data of %s", fileName));
        }
        if (this.data == null) this.data = new HashSet<T>();
    }

    /**
     * Retrieves the data object with the specified ID.
     *
     * @param id the ID of the data object to retrieve
     * @return the data object with the specified ID, or null if not found
     */
    public abstract T get(String id);

    /**
     * Retrieves all the data objects managed by this DataManager.
     *
     * @return a list of all the data objects
     */
    public Set<T> getAll() {
        return data;
    };

    /**
     * Adds a new data object to be managed by this DataManager.
     *
     * @param t the data object to add
     */
    public abstract void add(T t);

    /**
     * Updates an existing data object managed by this DataManager.
     *
     * @param t the data object to update
     * @return true if the update was successful, false otherwise
     */
    public abstract boolean update(T t);

    /**
     * Deletes the data object with the specified ID from this DataManager.
     *
     * @param id the ID of the data object to delete
     * @return true if the deletion was successful, false otherwise
     */
    public abstract boolean delete(String id);

    /**
     * Deletes all the data objects managed by this DataManager.
     */
    public abstract void deleteAll();

    /**
     * Saves the data managed by this DataManager to a file.
     *
     * @return true if the data was successfully saved, false otherwise
     */
    public boolean saveData() {
        Gson gson = new Gson();
        String json = gson.toJson(data, dataType.getType());

        try {
            // Save the data to a file
            File file = new File(String.format("./data/%s.json", fileName));
            FileUtils.createParentDirectories(file);
            FileUtils.writeStringToFile(file, json, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    
}
