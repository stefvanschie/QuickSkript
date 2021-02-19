package com.github.stefvanschie.quickskript.core.util.literal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents all possible weather types
 *
 * @since 0.1.0
 */
public enum WeatherType {

    /**
     * Clear weather with no weather effects. Depending on the time of day, the sun or moon is visible.
     *
     * @since 0.1.0
     */
    CLEAR("clear", "sun", "sunny"),

    /**
     * Rainy weather or snow when in high altitudes or cold biomes. Rain may be absent in warm biomes.
     *
     * @since 0.1.0
     */
    RAIN("rain", "rainy", "raining"),

    /**
     * Thunderstorm, including rain and occasional lightning strikes.
     *
     * @since 0.1.0
     */
    THUNDERSTORM("thunder", "thundering", "thunderstorm");

    /**
     * The different names of this weather type.
     */
    @NotNull
    private final String[] names;

    /**
     * All weather types by name.
     */
    @NotNull
    private static final Map<String, WeatherType> ENTRIES = new HashMap<>();

    /**
     * Creates a weather type with the specified names.
     *
     * @param names the weather type's names
     * @since 0.1.0
     */
    WeatherType(String... names) {
        this.names = names;
    }

    /**
     * Gets the names assigned to this weather type.
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
     * Gets a weather type by the specified name or null if no such weather type exists.
     *
     * @param name the name of the weather type
     * @return the weather type or null
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public static WeatherType byName(@NotNull String name) {
        return ENTRIES.get(name);
    }

    static {
        for (WeatherType weatherType : WeatherType.values()) {
            for (String name : weatherType.getNames()) {
                ENTRIES.put(name, weatherType);
            }
        }
    }
}
