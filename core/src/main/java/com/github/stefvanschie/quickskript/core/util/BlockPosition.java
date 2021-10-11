package com.github.stefvanschie.quickskript.core.util;

import org.jetbrains.annotations.Contract;

/**
 * An immutable data class for positions aligned to blocks. This does not include a world.
 *
 * @since 0.1.0
 */
public class BlockPosition {

    /**
     * The coordinates of this block position
     */
    private final int x, y, z;

    /**
     * Creates a new block position with the given coordinates.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     * @since 0.1.0
     */
    public BlockPosition(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Gets the x coordinate of this block position.
     *
     * @return the x coordinate
     * @since 0.1.0
     */
    @Contract(pure = true)
    public int getX() {
        return x;
    }

    /**
     * Gets the y coordinate of this block position.
     *
     * @return the y coordinate
     * @since 0.1.0
     */
    @Contract(pure = true)
    public int getY() {
        return y;
    }

    /**
     * Gets the z coordinate of this block position.
     *
     * @return the z coordinate
     * @since 0.1.0
     */
    @Contract(pure = true)
    public int getZ() {
        return z;
    }
}
