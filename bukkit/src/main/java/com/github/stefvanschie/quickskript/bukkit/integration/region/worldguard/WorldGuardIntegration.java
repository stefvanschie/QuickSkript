package com.github.stefvanschie.quickskript.bukkit.integration.region.worldguard;

import com.github.stefvanschie.quickskript.bukkit.integration.region.RegionIntegration;
import com.github.stefvanschie.quickskript.bukkit.util.LocationUtil;
import com.github.stefvanschie.quickskript.core.util.literal.Location;
import com.github.stefvanschie.quickskript.core.util.literal.Region;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
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
     * Gets whether the provided player can build in the provided location, under the restrictions of WorldGuard.
     *
     * @param player the player to check
     * @param location the location to check
     * @return true if the provided player can build in the provided location, false otherwise
     * @since 0.1.0
     */
    @Contract(pure = true)
    public boolean canBuild(@NotNull Player player, @NotNull Location location) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(RegionIntegration.WORLD_GUARD_NAME);

        if (!(plugin instanceof WorldGuardPlugin)) {
            return true; //plugin isn't enabled, or some other plugin pretends to be WorldGuard, ignore it
        }

        org.bukkit.Location bukkitLocation = LocationUtil.convert(location);

        if (bukkitLocation == null) {
            throw new IllegalArgumentException(
                "Unable to find Bukkit world with name '" + location.getWorld().getName() + "'"
            );
        }

        RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();

        return query.testBuild(BukkitAdapter.adapt(bukkitLocation), ((WorldGuardPlugin) plugin).wrapPlayer(player));
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
