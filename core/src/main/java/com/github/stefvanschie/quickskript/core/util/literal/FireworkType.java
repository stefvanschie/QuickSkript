package com.github.stefvanschie.quickskript.core.util.literal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents possible types of firework
 *
 * @since 0.1.0
 */
public enum FireworkType {

    /**
     * A ball firework type
     *
     * @since 0.1.0
     */
    BALL,

    /**
     * A burst firework type
     *
     * @since 0.1.0
     */
    BURST,

    /**
     * A creeper firework type
     *
     * @since 0.1.0
     */
    CREEPER,

    /**
     * A large ball firework type
     *
     * @since 0.1.0
     */
    LARGE_BALL,

    /**
     * A star firework type
     *
     * @since 0.1.0
     */
    STAR;

    /**
     * The entries of this enum by name
     */
    @NotNull
    private static final Map<String, FireworkType> ENTRIES = new HashMap<>();

    /**
     * Gets a firework type by name or null if no such firework type exists.
     *
     * @param name the name of the firework type
     * @return the firework type or null
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public static FireworkType byName(@NotNull String name) {
        return ENTRIES.get(name);
    }

    static {
        for (FireworkType fireworkType : FireworkType.values()) {
            ENTRIES.put(fireworkType.name().replace('_', ' ').toLowerCase(), fireworkType);
        }
    }
}
