package com.hankaji.icm.system;
/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: Lanterna, Gson, Apache Commons IO
*/

import java.util.Collection;
import java.util.Optional;

import com.hankaji.icm.lib.GsonSerializable;

public interface CRUD<T extends GsonSerializable> {
    /**
     * Retrieves the data object with the specified ID.
     *
     * @param id the ID of the data object to retrieve
     * @return the data object with the specified ID, or null if not found
     */
    public abstract Optional<T> getById(String id);

    /**
     * Retrieves all the data objects managed by database.
     *
     * @return a list of all the data objects
     */
    public Collection<T> getAll();

    /**
     * Adds a new data object to be managed by database.
     *
     * @param t the data object to add
     */
    public abstract void add(T t);

    /**
     * Updates an existing data object managed by database.
     *
     * @param t the data object to update
     * @param params the parameters to update the data object with
     * @return true if the update was successful, false otherwise
     */
    public abstract void update(T t);

    /**
     * Deletes the data object with the specified ID from database.
     *
     * @param id the ID of the data object to delete
     * @return true if the deletion was successful, false otherwise
     */
    public abstract void delete(T t);

}
