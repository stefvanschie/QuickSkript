package com.github.stefvanschie.quickskript.core.util.literal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents possible genes for pandas
 *
 * @since 0.1.0
 */
public enum Gene {

    /**
     * The gene for a aggressive panda
     *
     * @since 0.1.0
     */
    AGGRESSIVE,

    /**
     * The gene for a brown panda
     *
     * @since 0.1.0
     */
    BROWN,

    /**
     * The gene for a lazy panda
     *
     * @since 0.1.0
     */
    LAZY,

    /**
     * The gene for a normal panda
     *
     * @since 0.1.0
     */
    NORMAL,

    /**
     * The gene for a playful panda
     *
     * @since 0.1.0
     */
    PLAYFUL,

    /**
     * The gene for a weak panda
     *
     * @since 0.1.0
     */
    WEAK,

    /**
     * The gene for a worried panda
     *
     * @since 0.1.0
     */
    WORRIED;

    /**
     * All genes by their name
     */
    @NotNull
    private static final Map<String, Gene> ENTRIES = new HashMap<>();

    /**
     * Gets the gene by the given name or null if no such gene exists.
     *
     * @param name the name of the gene
     * @return the gene or null
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public static Gene byName(@NotNull String name) {
        return ENTRIES.get(name);
    }

    static {
        for (Gene gene : Gene.values()) {
            ENTRIES.put(gene.name().replace('_', ' ').toLowerCase(), gene);
        }
    }
}
