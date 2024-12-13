package com.github.stefvanschie.quickskript.core.util.literal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A block is a position in the world.
 *
 * @since 0.1.0
 */
public class Block {

    /**
     * The location this block is at.
     */
    @NotNull
    private final Location location;

    /**
     * Creates a new block at the given location.
     *
     * @param location the location
     * @since 0.1.0
     */
    public Block(@NotNull Location location) {
        this.location = location;
    }

    /**
     * Gets the location this block is positioned at.
     *
     * @return the location
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Location getLocation() {
        return location;
    }
}
