package com.hankaji.icm.lib;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javafx.scene.Node;

/**
 * Utility class containing various helper methods.
 */
public class Utils {

    /**
     * Extends a collection with additional elements.
     *
     * @param <T>       The type of the elements in the collection
     * @param original  The original collection
     * @param elements  The elements to add to the collection
     * @return A new collection that contains all the elements of the original collection and the elements passed as arguments
     */
    @SafeVarargs
    public static <T> Collection<T> extendsCollection(Collection<T> original, T... elements) {
        Collection<T> newCollection = new ArrayList<>(original);
        for (T element : elements) {
            newCollection.add(element);
        }
        return newCollection;
    }

    /**
     * Checks if an ID exists in a collection of IDs.
     *
     * @param id  The ID to check
     * @param ids The collection of IDs
     * @return true if the ID exists in the collection, false otherwise
     */
    public static boolean isIDExisted(String id, Collection<String> ids) {
        return ids.contains(id);
    }

    /**
     * Creates folders at the specified paths.
     *
     * @param paths The paths of the folders to create
     */
    public static void createFolders(String[] paths) {
        if (paths.length == 0) {
            System.out.println("Please provide the file name as an argument.");
            System.exit(1);
        }

        for (String folderPath : paths) {
            File file = new File(folderPath);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }

    /**
     * Returns the value obtained from a supplier, or a default value if an exception occurs.
     *
     * @param toGetValue   The supplier to get the value from
     * @param defaultValue The default value to return if an exception occurs
     * @param <T>          The type of the value
     * @return The value obtained from the supplier, or the default value if an exception occurs
     */
    public static <T> T nullOrDefault(Supplier<T> toGetValue, T defaultValue) {
        try {
            return toGetValue.get();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Returns the value obtained from a supplier, or an empty string if an exception occurs.
     *
     * @param toGetValue The supplier to get the value from
     * @return The value obtained from the supplier, or an empty string if an exception occurs
     */
    public static String nullOrDefault(Supplier<String> toGetValue) {
        return nullOrDefault(toGetValue, "");
    }

    /**
     * Extracts a substring from a string based on a regular expression pattern.
     *
     * @param pattern The regular expression pattern
     * @param str     The input string
     * @return An Optional containing the extracted substring, or an empty Optional if no match is found
     */
    public static Optional<String> extractFromStr(Pattern pattern, String str) {
        return Optional.ofNullable(pattern.matcher(str).group(1));
    }

    public static String capitalize(String str, String delimiter) {
        final String lowerCaseStr = str.toLowerCase();
        return Stream.of(lowerCaseStr.split(delimiter)).map(word -> {
            return Character.toUpperCase(word.charAt(0)) + word.substring(1);
        }).reduce("", (acc, word) -> acc + word + " ");
    }

    public static void enable(Node node) {
        node.setVisible(true);
        node.setManaged(true);
    }

    public static void disable(Node node) {
        node.setVisible(false);
        node.setManaged(false);
    }

    public static void toggle(Node node) {
        node.setVisible(!node.isVisible());
        node.setManaged(!node.isManaged());
    }

}
