package com.github.stefvanschie.quickskript.core.util.literal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents possible colors
 *
 * @since 0.1.0
 */
public enum Color {

    /**
     * A black color
     *
     * @since 0.1.0
     */
    BLACK("black"),

    /**
     * A blue color
     *
     * @since 0.1.0
     */
    BLUE("blue", "dark blue"),

    /**
     * A brown color
     *
     * @since 0.1.0
     */
    BROWN("brown", "indigo"),

    /**
     * A cyan color
     *
     * @since 0.1.0
     */
    CYAN("cyan", "aqua", "dark cyan", "dark aqua"),

    /**
     * A dark gray color
     *
     * @since 0.1.0
     */
    DARK_GRAY("dark grey", "dark gray"),

    /**
     * A dark yellow color
     *
     * @since 0.1.0
     */
    DARK_YELLOW("orange", "gold", "dark yellow"),

    /**
     * A gray color
     *
     * @since 0.1.0
     */
    GRAY("grey", "light grey", "gray", "light gray", "silver"),

    /**
     * A green color
     *
     * @since 0.1.0
     */
    GREEN("green", "dark green"),

    /**
     * A light blue color
     *
     * @since 0.1.0
     */
    LIGHT_BLUE("light blue", "light cyan", "light aqua"),

    /**
     * A light green color
     *
     * @since 0.1.0
     */
    LIGHT_GREEN("light green", "lime", "lime green"),

    /**
     * A light purple color
     *
     * @since 0.1.0
     */
    LIGHT_PURPLE("magenta", "light purple"),

    /**
     * A light red color
     *
     * @since 0.1.0
     */
    LIGHT_RED("pink", "light red"),

    /**
     * A purple color
     *
     * @since 0.1.0
     */
    PURPLE("purple", "dark purple"),

    /**
     * A red color
     *
     * @since 0.1.0
     */
    RED("red", "dark red"),

    /**
     * A white color
     *
     * @since 0.1.0
     */
    WHITE("white"),

    /**
     * A yellow color
     *
     * @since 0.1.0
     */
    YELLOW("yellow", "light yellow");

    /**
     * An array of possible ways to name this color
     */
    @NotNull
    private final String[] names;

    /**
     * All entries in this enum by their names
     */
    @NotNull
    private static final Map<String, Color> ENTRIES = new HashMap<>();

    /**
     * Creates a new color with an array of names for this color
     *
     * @param names the names for this color
     * @since 0.1.0
     */
    Color(@NotNull String... names) {
        this.names = names;
    }

    /**
     * Gets all the possible names for this color
     *
     * @return the names
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public String[] getNames() {
        return names;
    }

    /**
     * Gets the color by the given name, or null if no such color exists.
     *
     * @param name the name of the color
     * @return the color or null
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public static Color byName(@NotNull String name) {
        return ENTRIES.get(name);
    }

    static {
        for (Color color : Color.values()) {
            for (String name : color.getNames()) {
                ENTRIES.put(name, color);
            }
        }
    }
}
