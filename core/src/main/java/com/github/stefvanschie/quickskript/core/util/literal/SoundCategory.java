package com.github.stefvanschie.quickskript.core.util.literal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the different sound categories
 *
 * @since 0.1.0
 */
public enum SoundCategory {

    /**
     * The ambient sound category
     *
     * @since 0.1.0
     */
    AMBIENT,

    /**
     * The block sound category
     *
     * @since 0.1.0
     */
    BLOCK,

    /**
     * The hostile sound category
     *
     * @since 0.1.0
     */
    HOSTILE,

    /**
     * The master sound category
     *
     * @since 0.1.0
     */
    MASTER,

    /**
     * The music sound category
     *
     * @since 0.1.0
     */
    MUSIC,

    /**
     * The neutral sound category
     *
     * @since 0.1.0
     */
    NEUTRAL,

    /**
     * The player sound category
     *
     * @since 0.1.0
     */
    PLAYER,

    /**
     * The record sound category
     *
     * @since 0.1.0
     */
    RECORD,

    /**
     * The voice sound category
     *
     * @since 0.1.0
     */
    VOICE,

    /**
     * The weather sound category
     *
     * @since 0.1.0
     */
    WEATHER;

    /**
     * All sound categories by name
     */
    private static final Map<String, SoundCategory> ENTRIES = new HashMap<>();

    /**
     * Gets a sound category by name or null if no such sound category exists.
     *
     * @param name the name of the sound category
     * @return the sound category or null
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public static SoundCategory byName(@NotNull String name) {
        return ENTRIES.get(name);
    }

    static {
        for (SoundCategory soundCategory : SoundCategory.values()) {
            ENTRIES.put(soundCategory.name().replace('_', ' ').toLowerCase(), soundCategory);
        }
    }
}
