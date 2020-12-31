package com.github.stefvanschie.quickskript.core.util.literal;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents all possible tree types
 *
 * @since 0.1.0
 */
public enum TreeType {

    /**
     * An acacia tree
     *
     * @since 0.1.0
     */
    ACACIA(SkriptPattern.parse("acacia[s| tree[s]]")),

    /**
     * A big jungle tree
     *
     * @since 0.1.0
     */
    BIG_JUNGLE(SkriptPattern.parse("(big|tall|huge|giant|large) jungle[s| tree[s]]")),

    /**
     * A big redwood tree
     *
     * @since 0.1.0
     */
    BIG_REDWOOD(SkriptPattern.parse("(big|tall) (fir|pine|spruce|redwood)[s| tree[s]]")),

    /**
     * A big regular tree
     *
     * @since 0.1.0
     */
    BIG_REGULAR(SkriptPattern.parse("(big|large) (regular|normal|oak)[s| tree[s]]")),

    /**
     * A birch tree
     *
     * @since 0.1.0
     */
    BIRCH(SkriptPattern.parse("birch[es| tree[s]]")),

    /**
     * A brown mushroom
     *
     * @since 0.1.0
     */
    BROWN_MUSHROOM(SkriptPattern.parse("(huge|giant|large|big) brown mushroom[s]")),

    /**
     * A cocoa tree
     *
     * @since 0.1.0
     */
    COCOA_TREE(SkriptPattern.parse("cocoa tree[s]")),

    /**
     * A dark oak tree
     *
     * @since 0.1.0
     */
    DARK_OAK(SkriptPattern.parse("dark oak[s| tree[s]]")),

    /**
     * A jungle tree
     *
     * @since 0.1.0
     */
    JUNGLE(SkriptPattern.parse("[any] jungle[s| tree[s]]")),

    /**
     * A jungle bush tree
     *
     * @since 0.1.0
     */
    JUNGLE_BUSH(SkriptPattern.parse("[jungle] bush[es]")),

    /**
     * A mega redwood tree
     *
     * @since 0.1.0
     */
    MEGA_REDWOOD(SkriptPattern.parse("(mega|giant) (fir|pine|spruce|redwood)[s| tree[s]]")),

    /**
     * A mushroom
     *
     * @since 0.1.0
     */
    MUSHROOM(SkriptPattern.parse("[any] (huge|giant|large|big) mushroom[s]")),

    /**
     * A red mushroom
     *
     * @since 0.1.0
     */
    RED_MUSHROOM(SkriptPattern.parse("(huge|giant|large|big) red mushroom[s]")),

    /**
     * A redwood tree
     *
     * @since 0.1.0
     */
    REDWOOD(SkriptPattern.parse("[any] (fir|pine|spruce|redwood)[s| tree[s]]")),

    /**
     * A regular tree
     *
     * @since 0.1.0
     */
    REGULAR(SkriptPattern.parse("[any] (regular|normal|oak)[s| tree[s]]")),

    /**
     * A small jungle tree
     *
     * @since 0.1.0
     */
    SMALL_JUNGLE(SkriptPattern.parse("small jungle[s| tree[s]]")),

    /**
     * A small redwood tree
     *
     * @since 0.1.0
     */
    SMALL_REDWOOD(SkriptPattern.parse("small (fir|pine|spruce|redwood)[s| tree[s]]")),

    /**
     * A small regular tree
     *
     * @since 0.1.0
     */
    SMALL_REGULAR(SkriptPattern.parse("small (regular|normal|oak)[s| tree[s]]")),

    /**
     * A swamp tree
     *
     * @since 0.1.0
     */
    SWAMP(SkriptPattern.parse("swamp tree[s]")),

    /**
     * A tall birch tree
     *
     * @since 0.1.0
     */
    TALL_BIRCH(SkriptPattern.parse("(tall|big) birch[es| tree[s]]")),

    /**
     * Any tree
     *
     * @since 0.1.0
     */
    TREE(SkriptPattern.parse("[any] tree[s]"));

    /**
     * The names for this tree type. This collection is unmodifiable.
     */
    @NotNull
    private final Collection<String> names;

    /**
     * A mapping between the names of a tree type and the tree type for quick lookup by name
     */
    @NotNull
    private static final Map<String, TreeType> MAPPING = new HashMap<>();

    /**
     * Creates a tree type with the specified names
     *
     * @param pattern the pattern for the names of this tree type
     * @since 0.1.0
     */
    TreeType(@NotNull SkriptPattern pattern) {
        this.names = pattern.unrollFully();
    }

    /**
     * Gets all the names associated with this tree type. The returned collection is unmodifiable.
     *
     * @return the names
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    private Collection<String> getNames() {
        return names;
    }

    /**
     * Gets the tree type that belongs to the provided name, or null if the provided name doesn't belong to any tree
     * type.
     *
     * @param name the name of the tree type
     * @return the corresponding tree type or null
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public static TreeType byName(@NotNull String name) {
        return MAPPING.get(name);
    }

    static {
        for (TreeType treeType : TreeType.values()) {
            for (String name : treeType.getNames()) {
                MAPPING.put(name, treeType);
            }
        }
    }
}
