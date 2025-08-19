package com.github.stefvanschie.quickskript.core.util.literal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an enchantment either with or without a level
 *
 * @since 0.1.0
 */
public class EnchantmentType {

    /**
     * The type of enchantment
     */
    @NotNull
    private final Enchantment enchantment;

    /**
     * The level of the enchantment or null if not specified
     */
    @Nullable
    private final Integer level;

    /**
     * Creates a new enchantment with an optional level
     *
     * @param enchantment the enchantment
     * @param level the level or null if the enchantment has no level
     * @since 0.1.0
     */
    public EnchantmentType(@NotNull Enchantment enchantment, @Nullable Integer level) {
        this.enchantment = enchantment;
        this.level = level;
    }

    /**
     * Gets the enchantment.
     *
     * @return the enchantment
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Enchantment getEnchantment() {
        return this.enchantment;
    }

    /**
     * Gets the level of this enchantment. If the level was not set, this returns 1.
     *
     * @return the level
     * @since 0.1.0
     */
    @Contract(pure = true)
    public int getLevel() {
        return this.level == null ? 1 : this.level;
    }
}
