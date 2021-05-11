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
    BLACK(0x1E1B1B, "black"),

    /**
     * A blue color
     *
     * @since 0.1.0
     */
    BLUE(0x253192, "blue", "dark blue"),

    /**
     * A brown color
     *
     * @since 0.1.0
     */
    BROWN(0x51301A, "brown", "indigo"),

    /**
     * A cyan color
     *
     * @since 0.1.0
     */
    CYAN(0x287697, "cyan", "aqua", "dark cyan", "dark aqua"),

    /**
     * A dark gray color
     *
     * @since 0.1.0
     */
    DARK_GRAY(0x434343, "dark grey", "dark gray"),

    /**
     * A dark yellow color
     *
     * @since 0.1.0
     */
    DARK_YELLOW(0xEB8844, "orange", "gold", "dark yellow"),

    /**
     * A gray color
     *
     * @since 0.1.0
     */
    GRAY(0xABABAB, "grey", "light grey", "gray", "light gray", "silver"),

    /**
     * A green color
     *
     * @since 0.1.0
     */
    GREEN(0x3B511A, "green", "dark green"),

    /**
     * A light blue color
     *
     * @since 0.1.0
     */
    LIGHT_BLUE(0x6689D3, "light blue", "light cyan", "light aqua"),

    /**
     * A light green color
     *
     * @since 0.1.0
     */
    LIGHT_GREEN(0x41CD34, "light green", "lime", "lime green"),

    /**
     * A light purple color
     *
     * @since 0.1.0
     */
    LIGHT_PURPLE(0xC354CD, "magenta", "light purple"),

    /**
     * A light red color
     *
     * @since 0.1.0
     */
    LIGHT_RED(0xD88198, "pink", "light red"),

    /**
     * A purple color
     *
     * @since 0.1.0
     */
    PURPLE(0x7B2FBE, "purple", "dark purple"),

    /**
     * A red color
     *
     * @since 0.1.0
     */
    RED(0xB3312C, "red", "dark red"),

    /**
     * A white color
     *
     * @since 0.1.0
     */
    WHITE(0xF0F0F0, "white"),

    /**
     * A yellow color
     *
     * @since 0.1.0
     */
    YELLOW(0xDECF2A, "yellow", "light yellow");

    /**
     * The firework color of this color
     */
    private final int fireworkColor;

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
     * @param fireworkColor the firework color
     * @param names the names for this color
     * @since 0.1.0
     */
    Color(int fireworkColor, @NotNull String... names) {
        this.fireworkColor = fireworkColor;
        this.names = names;
    }

    /**
     * Gets the firework color of this color.
     *
     * @return the firework color
     * @since 0.1.0
     */
    @Contract(pure = true)
    public int getFireworkColor() {
        return fireworkColor;
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
