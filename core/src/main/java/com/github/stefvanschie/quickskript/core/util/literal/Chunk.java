package com.github.stefvanschie.quickskript.core.util.literal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * A chunk represents a 16-by-16 area in a Minecraft world.
 *
 * @since 0.1.0
 */
public class Chunk {

    /**
     * The world in which the chunk is located.
     */
    @NotNull
    private final World world;

    /**
     * The x and z coordinates of the chunk.
     */
    private final int x, z;

    /**
     * Creates a chunk which contains the provided location.
     *
     * @param location the location that is contained in the to-be-created chunk
     * @since 0.1.0
     */
    public Chunk(@NotNull Location location) {
        this.world = location.getWorld();
        this.x = (int) Math.floor(location.getX()) >> 4;
        this.z = (int) Math.floor(location.getZ()) >> 4;
    }

    @Contract(pure = true)
    @Override
    public boolean equals(@Nullable Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Chunk chunk = (Chunk) object;

        return this.world == chunk.world && this.x == chunk.x && this.z == chunk.z;
    }

    @Contract(pure = true)
    @Override
    public int hashCode() {
        return Objects.hash(this.world, this.x, this.z);
    }
}
