package com.github.stefvanschie.quickskript.core.util.literal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a region which occupies a certain area in a world.
 *
 * @since 0.1.0
 */
public interface Region {

    /**
     * Gets whether the provided coordinates are inside this region. This does not check the world of the region. If the
     * coordinates are inside the region, this returns true. Otherwise, false is returned.
     *
     * @param blockX the x coordinate
     * @param blockY the y coordinate
     * @param blockZ the z coordinate
     * @return true if the coordinates are inside this region, false otherwise
     * @since 0.1.0
     */
    @Contract(pure = true)
    boolean contains(int blockX, int blockY, int blockZ);

    /**
     * Gets the name of this region
     *
     * @return this region's name
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    String getName();
}
