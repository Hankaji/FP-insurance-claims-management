package com.hankaji.icm.lib;

import javafx.util.StringConverter;

/**
 * A string converter for converting between enum values and strings.
 * Only works with enums that have a proper case string representation.
 * <p>
 * The enum values must be in all uppercase with underscores separating words.
 * The string representation will be in title case with spaces separating words.
 * <p>
 * 
 * <p>
 * Example:
 * <pre>
 * {@code
 * public enum Day {
 *    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
 * }
 * 
 * EnumStringConverter<Day> converter = new EnumStringConverter<>(Day.class);
 * String string = converter.toString(Day.MONDAY); // "Monday"
 * </pre>
 *
 * @param <T> the enum type
 */
public class EnumStringConverter<T extends Enum<T>> extends StringConverter<T> {
    private final Class<T> enumType;

    public EnumStringConverter(Class<T> enumType) {
        this.enumType = enumType;
    }

    @Override
    public String toString(T object) {
        if (object == null) {
            return "";
        }
        // Convert enum to a proper case string
        String name = object.name().replace('_', ' ').toLowerCase();
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    @Override
    public T fromString(String string) {
        if (string == null || string.isEmpty()) {
            return null;
        }
        // Convert string back to enum value
        String name = string.replace(' ', '_').toUpperCase();
        return T.valueOf(enumType, name);
    }
}