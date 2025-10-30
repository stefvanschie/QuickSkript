package com.github.stefvanschie.quickskript.paper.util;

import com.github.stefvanschie.quickskript.core.util.literal.TreeType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A utility class for utility methods related to tree types.
 *
 * @since 0.1.0
 */
public class TreeTypeUtil {

    /**
     * A mapping between QuickSkript tree types and Bukkit tree types
     */
    @NotNull
    private static final Map<TreeType, Collection<org.bukkit.TreeType>> MAPPINGS = new HashMap<>();

    /**
     * Converts a QuickSkript tree type into Bukkit tree types. This throws an {@link IllegalStateException} when the
     * provided tree type can't be found.
     *
     * @param treeType the QuickSkript tree type to convert
     * @return the Bukkit tree types
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public static Collection<org.bukkit.TreeType> convert(@NotNull TreeType treeType) {
        Collection<org.bukkit.TreeType> bukkitTreeTypes = MAPPINGS.get(treeType);

        if (bukkitTreeTypes == null) {
            throw new IllegalStateException("Tree type doesn't have a mapping");
        }

        return bukkitTreeTypes;
    }

    static {
        MAPPINGS.put(TreeType.ACACIA, Set.of(org.bukkit.TreeType.ACACIA));
        MAPPINGS.put(TreeType.BIG_JUNGLE, Set.of(org.bukkit.TreeType.JUNGLE));
        MAPPINGS.put(TreeType.BIG_REDWOOD, Set.of(org.bukkit.TreeType.TALL_REDWOOD));
        MAPPINGS.put(TreeType.BIG_REGULAR, Set.of(org.bukkit.TreeType.BIG_TREE));
        MAPPINGS.put(TreeType.BIRCH, Set.of(org.bukkit.TreeType.BIRCH));
        MAPPINGS.put(TreeType.BROWN_MUSHROOM, Set.of(org.bukkit.TreeType.BROWN_MUSHROOM));
        MAPPINGS.put(TreeType.COCOA_TREE, Set.of(org.bukkit.TreeType.COCOA_TREE));
        MAPPINGS.put(TreeType.DARK_OAK, Set.of(org.bukkit.TreeType.DARK_OAK));

        MAPPINGS.put(TreeType.JUNGLE, Set.of(
            org.bukkit.TreeType.SMALL_JUNGLE,
            org.bukkit.TreeType.JUNGLE
        ));

        MAPPINGS.put(TreeType.JUNGLE_BUSH, Set.of(org.bukkit.TreeType.JUNGLE_BUSH));
        MAPPINGS.put(TreeType.MEGA_REDWOOD, Set.of(org.bukkit.TreeType.MEGA_REDWOOD));

        MAPPINGS.put(TreeType.MUSHROOM, Set.of(
            org.bukkit.TreeType.RED_MUSHROOM,
            org.bukkit.TreeType.BROWN_MUSHROOM
        ));

        MAPPINGS.put(TreeType.RED_MUSHROOM, Set.of(org.bukkit.TreeType.RED_MUSHROOM));

        MAPPINGS.put(TreeType.REDWOOD, Set.of(
            org.bukkit.TreeType.REDWOOD,
            org.bukkit.TreeType.TALL_REDWOOD
        ));

        MAPPINGS.put(TreeType.REGULAR, Set.of(
            org.bukkit.TreeType.TREE,
            org.bukkit.TreeType.BIG_TREE
        ));

        MAPPINGS.put(TreeType.SMALL_JUNGLE, Set.of(org.bukkit.TreeType.SMALL_JUNGLE));
        MAPPINGS.put(TreeType.SMALL_REDWOOD, Set.of(org.bukkit.TreeType.REDWOOD));
        MAPPINGS.put(TreeType.SMALL_REGULAR, Set.of(org.bukkit.TreeType.TREE));
        MAPPINGS.put(TreeType.SWAMP, Set.of(org.bukkit.TreeType.SWAMP));
        MAPPINGS.put(TreeType.TALL_BIRCH, Set.of(org.bukkit.TreeType.TALL_BIRCH));

        MAPPINGS.put(TreeType.TREE, Set.of(
            org.bukkit.TreeType.ACACIA,
            org.bukkit.TreeType.BIG_TREE,
            org.bukkit.TreeType.BIRCH,
            org.bukkit.TreeType.COCOA_TREE,
            org.bukkit.TreeType.DARK_OAK,
            org.bukkit.TreeType.JUNGLE,
            org.bukkit.TreeType.MEGA_REDWOOD,
            org.bukkit.TreeType.REDWOOD,
            org.bukkit.TreeType.SMALL_JUNGLE,
            org.bukkit.TreeType.SWAMP,
            org.bukkit.TreeType.TALL_BIRCH,
            org.bukkit.TreeType.TALL_REDWOOD,
            org.bukkit.TreeType.TREE
        ));
    }
}
