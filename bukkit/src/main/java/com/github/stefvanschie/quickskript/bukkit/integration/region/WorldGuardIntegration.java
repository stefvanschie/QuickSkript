package com.github.stefvanschie.quickskript.bukkit.integration.region;

import com.github.stefvanschie.quickskript.core.util.BlockPosition;
import com.github.stefvanschie.quickskript.core.util.literal.Region;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
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
                ProtectedRegion region = entry.getValue();

                BlockVector3 maximumPoint = region.getMaximumPoint();
                BlockVector3 minimumPoint = region.getMinimumPoint();

                int maxX = maximumPoint.getBlockX();
                int maxY = maximumPoint.getBlockY();
                int maxZ = maximumPoint.getBlockZ();

                int minX = minimumPoint.getBlockX();
                int minY = minimumPoint.getBlockY();
                int minZ = minimumPoint.getBlockZ();

                var greaterPosition = new BlockPosition(maxX, maxY, maxZ);
                var lowerPosition = new BlockPosition(minX, minY, minZ);

                regions.add(new Region(entry.getKey(), world.getName(), greaterPosition, lowerPosition));
            }
        }

        return regions;
    }
}
