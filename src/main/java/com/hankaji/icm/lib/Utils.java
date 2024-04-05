package com.hankaji.icm.lib;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.GridLayout;

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

    public static TextColor useHex(String value) {
        return TextColor.Factory.fromString(value);
    }

    public static boolean isIDExisted(String id, Collection<String> ids) {
        return ids.contains(id);
    }

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

    public static <T> T nullOrDefault(Supplier<T> toGetValue, T defaultValue) {
        try {
            return toGetValue.get();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static String nullOrDefault(Supplier<String> toGetValue) {
        return nullOrDefault(toGetValue, "");
    }

    

    public static class LayoutUtils {

        public static GridLayout createGridLayoutwithCustomMargin(int cols, int marginTop, int marginRight, int marginBottom, int marginLeft) {
            return new GridLayout(cols)
                .setTopMarginSize(marginTop)
                .setRightMarginSize(marginRight)
                .setBottomMarginSize(marginBottom)
                .setLeftMarginSize(marginLeft);
        }
    
        public static GridLayout createGridLayoutwithCustomMargin(int cols, int marginHorizontal, int marginVertical) {
            return createGridLayoutwithCustomMargin(cols, marginVertical, marginHorizontal, marginVertical, marginHorizontal);
        }
    
        public static GridLayout createGridLayoutwithCustomMargin(int cols, int margin) {
            return createGridLayoutwithCustomMargin(cols, margin, margin);
        }

    }


}
