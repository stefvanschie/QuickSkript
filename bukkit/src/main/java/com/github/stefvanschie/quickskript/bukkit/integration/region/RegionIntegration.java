package com.github.stefvanschie.quickskript.bukkit.integration.region;

import com.github.stefvanschie.quickskript.core.util.literal.Region;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;

/**
 * Integrates with several plugins that provide regions.
 *
 * @since 0.1.0
 */
public class RegionIntegration {

    /**
     * Integration with world guard
     */
    @Nullable
    private WorldGuardIntegration worldGuard;

    /**
     * Integration with grief prevention
     */
    @Nullable
    private GriefPreventionIntegration griefPrevention;

    /**
     * Creates a new region integration. This loads several underlying integrations if possible.
     *
     * @since 0.1.0
     */
    public RegionIntegration() {
        if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
            worldGuard = new WorldGuardIntegration();
        }

        if (Bukkit.getPluginManager().isPluginEnabled("GriefPrevention")) {
            griefPrevention = new GriefPreventionIntegration();
        }
    }

    /**
     * Gets all combined regions of the enabled underlying integrations. If no integrations are available, this will
     * return an empty collection.
     *
     * @return all regions
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Collection<Region> getRegions() {
        Collection<Region> regions = new HashSet<>();

        if (worldGuard != null) {
            regions.addAll(worldGuard.getRegions());
        }

        if (griefPrevention != null) {
            regions.addAll(griefPrevention.getRegions());
        }

        return regions;
    }

    /**
     * Whether region integration is available. If at least one underlying integration exists then this will return
     * true, otherwise this returns false.
     *
     * @return true if region integration is available, false otherwise
     * @since 0.1.0
     */
    @Contract(pure = true)
    public boolean hasRegionIntegration() {
        return worldGuard != null || griefPrevention != null;
    }
}
