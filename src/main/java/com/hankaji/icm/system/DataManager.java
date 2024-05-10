package com.hankaji.icm.system;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hankaji.icm.lib.GsonSerializable;
import com.hankaji.icm.lib.Utils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.HashSet;
import java.util.Set;

/**
 * This abstract class represents a data manager that provides basic CRUD operations for managing data of a specific type.
 * It also provides functionality to load and save data from/to a file.
 *
 * @param <T> the type of data managed by this DataManager
 */
public abstract class DataManager<T extends GsonSerializable> {

    /**
     * The set of data managed by this DataManager.
     */
    protected Set<T> data;

    /**
     * The file name used for data storage.
     */
    protected String fileName;

    /**
     * The TypeToken object for the data type.
     */
    private final TypeToken<Set<T>> dataType;

    /**
     * The Gson object for JSON serialization/deserialization.
     */
    protected final Gson gson;

    /**
     * Constructs a DataManager object with the given class type.
     * The class type is used to determine the file name for data storage.
     * It also adds a shutdown hook to save data before the program exits.
     *
     * @param clazz     the class type of the data managed by this DataManager
     * @param typeToken the TypeToken object for the data type
     */
    protected DataManager(Class<T> clazz, TypeToken<Set<T>> typeToken) {
        this.fileName = clazz.getSimpleName();

        // create a Type object for the data type
        this.dataType = typeToken;

        // Create Gson instance
        this.gson = useGson();

        // Add a shutdown hook to save data before the program exits
        Runtime.getRuntime().addShutdownHook(new Thread(this::saveData));

        // Load data from file
        loadData();
    }

    /**
     * Retrieves the data managed by this DataManager.
     */
    public void loadData() {
        // Load data from file
        try {
            // Create the data folder if it does not exist
            Utils.createFolders(new String[]{"./data"});

            File file = new File(String.format("./data/%s.json", fileName));
            String json = FileUtils.readFileToString(file, "UTF-8");

            this.data = gson.fromJson(json, dataType.getType());
        } catch (NoSuchFileException e) {
            System.out.println(String.format("No data of %s found, initializing new data", fileName));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(String.format("Error loading data of %s", fileName));
            System.exit(1);
        }
        if (this.data == null) this.data = new HashSet<>();
    }

    /**
     * Saves the data managed by this DataManager to a file.
     *
     * @return true if the data was successfully saved, false otherwise
     */
    public boolean saveData() {

        String json = gson.toJson(data, dataType.getType());

        try {
            // Save the data to a file
            File file = new File(String.format("./data/%s.json", fileName));
            FileUtils.createParentDirectories(file);
            FileUtils.writeStringToFile(file, json, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Retrieves the data managed by this DataManager.
     * Overwrite this method to use a custom Gson object.
     *
     * @return the data managed by this DataManager
     */
    protected Gson useGson() {
        return new Gson();
    }

}