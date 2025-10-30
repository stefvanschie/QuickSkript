package com.github.stefvanschie.quickskript.spigot.integration.region.griefprevention;

import com.github.stefvanschie.quickskript.spigot.integration.region.RegionIntegration;
import com.github.stefvanschie.quickskript.spigot.util.LocationUtil;
import com.github.stefvanschie.quickskript.core.util.literal.Location;
import com.github.stefvanschie.quickskript.core.util.literal.Region;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
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
     * Gets whether the provided player can build in the provided location, under the restrictions of GriefPrevention.
     *
     * @param player the player to check
     * @param location the location to check
     * @return true if the provided player can build in the provided location, false otherwise
     * @since 0.1.0
     */
    @Contract(pure = true)
    public boolean canBuild(@NotNull Player player, @NotNull Location location) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(RegionIntegration.GRIEF_PREVENTION_NAME);

        if (!(plugin instanceof GriefPrevention)) {
            return true; //plugin isn't enabled, or some other plugin pretends to be GriefPrevention, ignore it
        }

        org.bukkit.Location bukkitLocation = LocationUtil.convert(location);

        if (bukkitLocation == null) {
            throw new IllegalArgumentException(
                "Unable to find Bukkit world with name '" + location.getWorld().getName() + "'"
            );
        }

        return ((GriefPrevention) plugin).allowBuild(player, bukkitLocation) == null;
    }

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
