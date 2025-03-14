package com.github.stefvanschie.quickskript.core.util.literal;

import org.jetbrains.annotations.Contract;

/**
 * Represents a container that can hold a single item stack.
 *
 * @since 0.1.0
 */
public interface Slot {

    /**
     * Checks whether the slot is empty.
     *
     * @return true if the slot is empty, false otherwise
     * @since 0.1.0
     */
    @Contract(pure = true)
    boolean isEmpty();
}
