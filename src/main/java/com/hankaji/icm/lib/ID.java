package com.hankaji.icm.lib;

/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: Lanterna, Gson, Apache Commons IO
*/

import java.util.Random;

/**
 * The ID class provides a utility method to generate random IDs of a specified
 * length.
 */
public class ID {

    private String id;

    private static String numbers = "1234567890";
    // private static String lowercaseAlpha = "abcdefghijklmnopqrstuvwxyz";
    // private static String uppercaseAlpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Constructs an ID object with the specified ID.
     *
     * @param id The ID to set.
     */
    private ID(String id) {
        this.id = id;
    }

    /**
     * Generates a random ID of the specified length.
     *
     * @param length The length of the ID to generate.
     * @return The generated ID.
     */
    public static ID generateID(int length) {

        StringBuilder id = new StringBuilder(length);

        Random ran = new Random();

        for (int i = 0; i < length; i++) {
            id.append(numbers.charAt(ran.nextInt(numbers.length())));
        }

        return new ID(id.toString());
    }

    public String prefix(String prefix) {
        return prefix + id;
    }

    // public static enum Identifier {
    // NUMBERS,
    // LOWERCASE_ALPHABET,
    // UPPERCASE_ALPHABET
    // }

    public String getId() {
        return id;
    }

}
