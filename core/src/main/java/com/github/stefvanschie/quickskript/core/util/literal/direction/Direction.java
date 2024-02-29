package com.github.stefvanschie.quickskript.core.util.literal.direction;

import com.github.stefvanschie.quickskript.core.util.literal.Location;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Marker interface to signify directions.
 *
 * @since 0.1.0
 */
public interface Direction {

    /**
     * Gets a new location with the transformation of this direction applied to it.
     *
     * @param location the original location
     * @return a new location, offset by this direction
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    Location getRelative(@NotNull Location location);
}
