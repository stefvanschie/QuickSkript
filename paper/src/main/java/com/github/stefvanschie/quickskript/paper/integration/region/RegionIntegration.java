package com.github.stefvanschie.quickskript.paper.integration.region;

import com.github.stefvanschie.quickskript.paper.integration.region.griefprevention.GriefPreventionIntegration;
import com.github.stefvanschie.quickskript.paper.integration.region.worldguard.WorldGuardIntegration;
import com.github.stefvanschie.quickskript.core.util.literal.Location;
import com.github.stefvanschie.quickskript.core.util.literal.Region;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
     * The name of the GriefPrevention plugin.
     */
    public static final String GRIEF_PREVENTION_NAME = "GriefPrevention";

    /**
     * The name of the WorldGuard plugin.
     */
    public static final String WORLD_GUARD_NAME = "WorldGuard";

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
     * Gets whether the provided player can build in the provided location, under the restrictions of the enabled region
     * plugins.
     *
     * @param player the player to check
     * @param location the location to check
     * @return true if the provided player can build in the provided location, false otherwise
     * @since 0.1.0
     */
    @Contract(pure = true)
    public boolean canBuild(@NotNull Player player, @NotNull Location location) {
        boolean canBuild = true;

        if (this.worldGuard != null) {
            canBuild &= this.worldGuard.canBuild(player, location);
        }

        if (this.griefPrevention != null) {
            canBuild &= this.griefPrevention.canBuild(player, location);
        }

        return canBuild;
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
     * Loads the underlying integrations. This can also be used to reload the underlying integrations.
     *
     * @since 0.1.0
     */
    public void loadIntegrations() {
        if (Bukkit.getPluginManager().isPluginEnabled(WORLD_GUARD_NAME)) {
            worldGuard = new WorldGuardIntegration();
        }

        if (Bukkit.getPluginManager().isPluginEnabled(GRIEF_PREVENTION_NAME)) {
            griefPrevention = new GriefPreventionIntegration();
        }
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
