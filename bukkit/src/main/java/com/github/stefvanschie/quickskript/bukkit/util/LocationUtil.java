package com.github.stefvanschie.quickskript.bukkit.util;

import com.github.stefvanschie.quickskript.core.util.literal.Location;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A utility class for locations.
 *
 * @since 0.1.0
 */
public class LocationUtil {

    /**
     * A private constructor to prevent construction.
     *
     * @since 0.1.0
     */
    private LocationUtil() {}

    /**
     * Converts a QuickSkript location in to a Bukkit location. If the Bukkit world cannot be found, this returns null.
     *
     * @param location the QuickSkript location
     * @return the Bukkit location
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public static org.bukkit.Location convert(@NotNull Location location) {
        World world = Bukkit.getWorld(location.getWorld().getName());

        if (world == null) {
            return null;
        }

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();

        return new org.bukkit.Location(world, x, y, z, yaw, pitch);
    }
}
