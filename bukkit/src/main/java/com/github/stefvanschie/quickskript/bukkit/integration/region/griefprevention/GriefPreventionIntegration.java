package com.github.stefvanschie.quickskript.bukkit.integration.region.griefprevention;

import com.github.stefvanschie.quickskript.core.util.literal.Region;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;

/**
 * Integration with GriefPrevention
 *
 * @since 0.1.0
 */
public class GriefPreventionIntegration {

    /**
     * Gets all regions.
     *
     * @return a collection of regions
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Collection<Region> getRegions() {
        Collection<Region> regions = new HashSet<>();

        for (Claim claim : GriefPrevention.instance.dataStore.getClaims()) {
            regions.add(new GriefPreventionRegion(claim));
        }

        return regions;
    }
}
