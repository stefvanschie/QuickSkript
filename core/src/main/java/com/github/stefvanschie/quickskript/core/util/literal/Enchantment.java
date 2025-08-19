package com.github.stefvanschie.quickskript.core.util.literal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * An enchantment.
 *
 * @since 0.1.0
 */
public class Enchantment {

    /**
     * The namespaced key of this enchantment.
     */
    @NotNull
    private final String key;

    /**
     * Creates a new enchantment with the given namespaced key.
     *
     * @param key the key
     * @since 0.1.0
     */
    public Enchantment(@NotNull String key) {
        this.key = key;
    }

    /**
     * Gets the namespaced key of this enchantment
     *
     * @return the key
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public String getKey() {
        return this.key;
    }
}
