package com.github.stefvanschie.quickskript.spigot.integration.region.worldguard;

import com.github.stefvanschie.quickskript.core.util.literal.Region;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.jetbrains.annotations.NotNull;

/**
 * A region from WorldGuard.
 *
 * @since 0.1.0
 */
public class WorldGuardRegion implements Region {

    /**
     * The name of the region.
     */
    @NotNull
    private final String name;

    /**
     * The WorldGuard protected region.
     */
    @NotNull
    private final ProtectedRegion region;

    /**
     * Creates a new region based on the underlying WorldGuard protected region and the name of the region.
     *
     * @param name the name
     * @param region the WorldGuard protected region
     * @since 0.1.0
     */
    public WorldGuardRegion(@NotNull String name, @NotNull ProtectedRegion region) {
        this.name = name;
        this.region = region;
    }

    @Override
    public boolean contains(int blockX, int blockY, int blockZ) {
        BlockVector3 maximumPoint = this.region.getMaximumPoint();
        BlockVector3 minimumPoint = this.region.getMinimumPoint();

        return blockX <= maximumPoint.getBlockX() && blockX >= minimumPoint.getBlockX()
            && blockY <= maximumPoint.getBlockY() && blockY >= minimumPoint.getBlockY()
            && blockZ <= maximumPoint.getBlockZ() && blockZ >= minimumPoint.getBlockZ();
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }
}
