package com.github.stefvanschie.quickskript.core.util.literal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a world
 *
 * @since 0.1.0
 */
public class World {

    /**
     * The name of the world
     */
    @NotNull
    private final String name;

    /**
     * Creates a new virtual world with the specified name. The world only exists virtually; no world is actually
     * created.
     *
     * @param name the name of the world
     * @since 0.1.0
     */
    public World(@NotNull String name) {
        this.name = name;
    }

    /**
     * Gets the name of this world.
     *
     * @return the name
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public String getName() {
        return name;
    }

    @Override
    @Contract(value = "null -> false", pure = true)
    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        World world = (World) object;

        return this.name.equals(world.name);
    }

    @Override
    @Contract(pure = true)
    public int hashCode() {
        return this.name.hashCode();
    }
}
