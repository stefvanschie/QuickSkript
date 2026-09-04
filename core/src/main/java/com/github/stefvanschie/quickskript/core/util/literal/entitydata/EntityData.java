package com.github.stefvanschie.quickskript.core.util.literal.entitydata;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a set of data for a given type of entity.
 *
 * @since 0.1.0
 */
public interface EntityData<T> {

    /**
     * Checks if the provided entity matches the provided type of the data class and matches the given data.
     *
     * @param entity the entity to compare this data against
     * @return true if the data matches, false otherwise
     * @since 0.1.0
     */
    boolean match(@NotNull T entity);
}
