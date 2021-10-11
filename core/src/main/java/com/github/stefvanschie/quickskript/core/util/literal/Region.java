package com.github.stefvanschie.quickskript.core.util.literal;

import com.github.stefvanschie.quickskript.core.util.BlockPosition;
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
     * The name of the world of this region
     */
    @NotNull
    private String world;

    /**
     * The greater block position of this region
     */
    @NotNull
    private BlockPosition greaterPosition;

    /**
     * The lower block position of this region
     */
    @NotNull
    private BlockPosition lowerPosition;

    /**
     * Creates a region with the provided name
     *
     * @param name the name of the region
     * @param world the name of the world in which this region exists
     * @param greaterPosition the greater block position
     * @param lowerPosition the lower block position
     * @since 0.1.0
     */
    public Region(@NotNull String name, @NotNull String world, @NotNull BlockPosition greaterPosition,
        @NotNull BlockPosition lowerPosition) {
        if (greaterPosition.getX() < lowerPosition.getX() ||
            greaterPosition.getY() < lowerPosition.getY() ||
            greaterPosition.getZ() < lowerPosition.getZ()) {
            throw new IllegalArgumentException("The greater block position has a coordinate lower than the lower block position");
        }

        this.name = name;
        this.world = world;
        this.greaterPosition = greaterPosition;
        this.lowerPosition = lowerPosition;
    }

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
    public boolean contains(int blockX, int blockY, int blockZ) {
        return lowerPosition.getX() <= blockX && blockX <= greaterPosition.getX() &&
            lowerPosition.getY() <= blockY && blockY <= greaterPosition.getY() &&
            lowerPosition.getZ() <= blockZ && blockZ <= greaterPosition.getZ();
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

    /**
     * Gets the name of the world in which this region resides.
     *
     * @return the name of the world
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public String getWorld() {
        return world;
    }
}
