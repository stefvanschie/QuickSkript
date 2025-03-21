package com.github.stefvanschie.quickskript.core.util.literal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An item with an amount and optionally an enchantment
 *
 * @since 0.1.0
 */
public class Item {

    /**
     * The item type
     */
    @NotNull
    private final String itemType;

    /**
     * The amount this item type has
     */
    private int amount;

    /**
     * The enchantment or null if this item doesn't have an enchantment
     */
    @Nullable
    private Enchantment enchantment;

    /**
     * Creates a new item with the given item type. The amount will be set the 1.
     *
     * @param itemType the item type of this item
     * @param amount the amount of items
     * @since 0.1.0
     */
    public Item(@NotNull String itemType, int amount) {
        this.itemType = itemType;
        this.amount = amount;
    }

    /**
     * Creates a new item with the given item type. The amount will be set the 1.
     *
     * @param itemType the item type of this item
     * @since 0.1.0
     */
    public Item(@NotNull String itemType) {
        this(itemType, 1);
    }

    /**
     * Sets the enchantment for this item
     *
     * @param enchantment the enchantment
     * @since 0.1.0
     */
    public void setEnchantment(@NotNull Enchantment enchantment) {
        this.enchantment = enchantment;
    }

    /**
     * Gets whether this item is empty.
     *
     * @return true if this item is empty, false otherwise
     * @since 0.1.0
     */
    @Contract(pure = true)
    public boolean isEmpty() {
        return this.amount == 0;
    }
}
