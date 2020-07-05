package com.github.stefvanschie.quickskript.core.util.literal;

import com.github.stefvanschie.quickskript.core.util.registry.ItemTypeRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a category of an item, combined with all the item types that belong to this category
 *
 * @since 0.1.0
 */
public class ItemCategory {

    /**
     * All item types that belong to this category
     */
    private Collection<ItemTypeRegistry.Entry> itemTypes;

    /**
     * The amount of item for this category
     */
    private int amount = 1;

    /**
     * Whether this category represents all items or just one
     */
    private boolean all = false;

    /**
     * All enchantments that these item types have. If {@link #all} is true then this set is for all item types,
     * otherwise it's for just one item types. If there are no item types, then this applies to none of them.
     */
    private final Set<Enchantment> enchantments = new HashSet<>();

    /**
     * Creates a new item category with the given item types.
     *
     * @param itemTypes the item types this category has
     * @since 0.1.0
     */
    public ItemCategory(@NotNull Collection<ItemTypeRegistry.Entry> itemTypes) {
        this.itemTypes = itemTypes;
    }

    /**
     * Makes this item category hold for all item types it matches, instead of just one.
     *
     * @since 0.1.0
     */
    public void all() {
        this.all = true;
    }

    /**
     * Sets the amount of items this category represents.
     *
     * @param amount the amount of items
     * @since 0.1.0
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Adds the provided enchantments to the enchantments in this item category.
     *
     * @param enchantment the enchantment to add
     * @since 0.1.0
     */
    public void addEnchantment(@NotNull Enchantment enchantment) {
        this.enchantments.add(enchantment);
    }
}
