package com.github.stefvanschie.quickskript.bukkit.integration.region.griefprevention;

import com.github.stefvanschie.quickskript.core.util.literal.Region;
import me.ryanhamshire.GriefPrevention.Claim;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * A region from GriefPrevention.
 *
 * @since 0.1.0
 */
public class GriefPreventionRegion implements Region {

    /**
     * The GriefPrevention claim.
     */
    @NotNull
    private final Claim claim;

    /**
     * Creates a new region based on the underlying GriefPrevention claim.
     *
     * @param claim the GriefPrevention claim
     * @since 0.1.0
     */
    public GriefPreventionRegion(@NotNull Claim claim) {
        this.claim = claim;
    }

    @Override
    public boolean contains(int blockX, int blockY, int blockZ) {
        Location greaterBoundaryCorner = this.claim.getGreaterBoundaryCorner();
        Location lowerBoundaryCorner = this.claim.getLesserBoundaryCorner();

        return blockX <= greaterBoundaryCorner.getBlockX() && blockX >= lowerBoundaryCorner.getBlockX()
            && blockY <= greaterBoundaryCorner.getBlockY() && blockY >= lowerBoundaryCorner.getBlockY()
            && blockZ <= greaterBoundaryCorner.getBlockZ() && blockZ >= lowerBoundaryCorner.getBlockZ();
    }

    @NotNull
    @Override
    public String getName() {
        return this.claim.getID().toString();
    }
}
