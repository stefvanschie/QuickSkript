package com.github.stefvanschie.quickskript.core.util.registry;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An interface for all registries.
 *
 * @param <T> the type of the registry
 * @since 0.1.0
 */
public interface Registry<T> {

    /**
     * Looks up an element in this registry by its name. Returns the corresponding element or null if no such element
     * exists.
     *
     * @param name the name to look up
     * @return the element or null
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    T byName(@NotNull String name);
}
