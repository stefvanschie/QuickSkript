package com.github.stefvanschie.quickskript.core.util.literal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the possible game modes a player can be in.
 *
 * @since 0.1.0
 */
public enum GameMode {

    /**
     * Represents the adventure game mode
     *
     * @since 0.1.0
     */
    ADVENTURE,

    /**
     * Represents the creative game mode
     *
     * @since 0.1.0
     */
    CREATIVE,

    /**
     * Represents the spectator game mode
     *
     * @since 0.1.0
     */
    SPECTATOR,

    /**
     * Represents the survival game mode
     *
     * @since 0.1.0
     */
    SURVIVAL;

    /**
     * The game modes by their name
     */
    @NotNull
    private static final Map<String, GameMode> ENTRIES = new HashMap<>();

    /**
     * Gets the game mode by its name or null if no such game mode exists.
     *
     * @param name the name of the game mode
     * @return the game mode or null
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public static GameMode byName(@NotNull String name) {
        return ENTRIES.get(name);
    }

    static {
        for (GameMode gameMode : GameMode.values()) {
            ENTRIES.put(gameMode.name().replace('_', ' ').toLowerCase(), gameMode);
        }
    }
}
