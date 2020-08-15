package com.github.stefvanschie.quickskript.core.util.registry;

import com.github.stefvanschie.quickskript.core.util.literal.Region;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A registry for the regions currently available
 *
 * @since 0.1.0
 */
public class RegionRegistry {

    /**
     * The regions
     */
    @NotNull
    private final Set<Region> regions = new HashSet<>();

    /**
     * Adds the provided region to this registry.
     *
     * @param regions the regions to add
     * @since 0.1.0
     */
    public void addRegions(@NotNull Collection<Region> regions) {
        this.regions.addAll(regions);
    }

    /**
     * Gets all the regions in this registry with the provided name.
     *
     * @param name the name of the region
     * @return a collection of the regions
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Collection<? extends Region> getRegions(@NotNull String name) {
        Collection<Region> regions = new HashSet<>();

        for (Region region : this.regions) {
            if (!region.getName().equals(name)) {
                continue;
            }

            regions.add(region);
        }

        return regions;
    }
}
