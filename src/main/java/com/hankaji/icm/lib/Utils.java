package com.hankaji.icm.lib;

import java.util.ArrayList;
import java.util.Collection;

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
