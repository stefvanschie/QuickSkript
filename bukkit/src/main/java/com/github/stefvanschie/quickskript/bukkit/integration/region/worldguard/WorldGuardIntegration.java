package com.github.stefvanschie.quickskript.bukkit.integration.region.worldguard;

import com.github.stefvanschie.quickskript.core.util.literal.Region;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

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

        for (World world : Bukkit.getWorlds()) {
            WorldGuard worldGuard = WorldGuard.getInstance();
            RegionManager regionManager = worldGuard.getPlatform().getRegionContainer().get(BukkitAdapter.adapt(world));

            if (regionManager == null) {
                continue;
            }

            for (Map.Entry<String, ProtectedRegion> entry : regionManager.getRegions().entrySet()) {
                regions.add(new WorldGuardRegion(entry.getKey(), entry.getValue()));
            }
        }

        return regions;
    }
}
