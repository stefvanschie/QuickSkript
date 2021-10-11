package com.github.stefvanschie.quickskript.bukkit.integration.region;

import com.github.stefvanschie.quickskript.core.util.BlockPosition;
import com.github.stefvanschie.quickskript.core.util.literal.Region;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Location;
import org.bukkit.World;
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
            Location greaterCorner = claim.getGreaterBoundaryCorner();
            Location lowerCorner = claim.getLesserBoundaryCorner();

            World world = greaterCorner.getWorld();

            if (!world.equals(lowerCorner.getWorld())) {
                throw new IllegalStateException("Region is in two different worlds");
            }

            int greaterX = greaterCorner.getBlockX();
            int greaterY = greaterCorner.getBlockY();
            int greaterZ = greaterCorner.getBlockZ();

            int lowerX = lowerCorner.getBlockX();
            int lowerY = lowerCorner.getBlockY();
            int lowerZ = lowerCorner.getBlockZ();

            var greaterPosition = new BlockPosition(greaterX, greaterY, greaterZ);
            var lowerPosition = new BlockPosition(lowerX, lowerY, lowerZ);

            regions.add(new Region(claim.getID().toString(), world.getName(), greaterPosition, lowerPosition));
        }

        return regions;
    }
}
