package com.hankaji.icm.lib;

import java.util.ArrayList;
import java.util.Collection;

public class Utils {

    /**
     * @param <T> The type of the elements in the collection
     * @param original The original collection
     * @param elements The elements to add to the collection
     * @return A new collection that contains all the elements of the original collection and the elements passed as arguments
     */
    @SafeVarargs
    public static <T> Collection<T> extendsCollection(Collection<T> original, T... elements ) {
        Collection<T> newCollection = new ArrayList<>(original);
        for (T element : elements) {
            newCollection.add(element);
        }
        return newCollection;
    }
}
