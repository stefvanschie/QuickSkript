package com.github.stefvanschie.quickskript.core.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * A cache for storing objects
 *
 * @since 0.1.0
 */
public class TemporaryCache {

    /**
     * A map which stores all objects with an identifier
     */
    private static final Map<String, Object> CACHE = new HashMap<>();

    /**
     * Adds a key value pair to the cache
     *
     * @param key the key as identifier
     * @param object the object to store
     * @param <T> the type of object to store
     * @since 0.1.0
     */
    public static <T> void set(@NotNull String key, @NotNull T object) {
        CACHE.put(key, object);
    }

    /**
     * Gets an object from the cache
     *
     * @param key the identifier
     * @param <T> the type of object
     * @return the cached object
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public static <T> T get(@NotNull String key) {
        return (T) CACHE.get(key);
    }
}
