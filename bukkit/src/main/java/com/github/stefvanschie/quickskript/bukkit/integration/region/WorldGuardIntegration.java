package com.github.stefvanschie.quickskript.bukkit.integration.region;

import com.github.stefvanschie.quickskript.core.util.literal.Region;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Integration with WorldGuard
 *
 * @since 0.1.0
 */
public class WorldGuardIntegration {

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
        List<RegionManager> regionManagers = WorldGuard.getInstance().getPlatform().getRegionContainer().getLoaded();

        for (RegionManager regionManager : regionManagers) {
            for (String name : regionManager.getRegions().keySet()) {
                regions.add(new Region(name));
            }
        }

        return regions;
    }
}
