package com.hankaji.icm.lib;

import java.util.Random;

public class ID {

    public static void main(String[] args) {
        System.out.println(ID.generateID(7));
    }

    private static String numbers = "1234567890";
    // private static String lowercaseAlpha = "abcdefghijklmnopqrstuvwxyz";
    // private static String uppercaseAlpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String generateID(int length) {

        StringBuilder id = new StringBuilder(length);

        Random ran = new Random();

        for (int i= 0; i < length; i++) {
            id.append(numbers.charAt(ran.nextInt(numbers.length())));
        }

        return id.toString();
    }

    // public static enum Identifier {
    //     NUMBERS,
    //     LOWERCASE_ALPHABET,
    //     UPPERCASE_ALPHABET
    // }
}
