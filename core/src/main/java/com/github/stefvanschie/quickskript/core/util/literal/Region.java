package com.github.stefvanschie.quickskript.core.util.literal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a region which occupies a certain area in a world.
 *
 * @since 0.1.0
 */
public class Region {

    /**
     * The name of this region
     */
    @NotNull
    private String name;

    /**
     * Creates a region with the provided name
     *
     * @param name the name of the region
     * @since 0.1.0
     */
    public Region(@NotNull String name) {
        this.name = name;
    }

    /**
     * Gets the name of this region
     *
     * @return this region's name
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public String getName() {
        return name;
    }
}
