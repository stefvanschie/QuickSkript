package com.github.stefvanschie.quickskript.paper.util.event.region;

import com.github.stefvanschie.quickskript.core.util.literal.Region;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A base class for region-related events.
 *
 * @since 0.1.0
 */
public abstract class RegionEvent extends Event {

    /**
     * The region this event responded to.
     */
    @NotNull
    private final Region region;

    /**
     * Creates a new region event.
     *
     * @param region the region this event is concerned with
     * @since 0.1.0
     */
    public RegionEvent(@NotNull Region region) {
        this.region = region;
    }

    /**
     * Gets the region associated with this event.
     *
     * @return the region
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Region getRegion() {
        return region;
    }
}
