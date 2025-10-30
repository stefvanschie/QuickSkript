package com.github.stefvanschie.quickskript.spigot.util;

import com.github.stefvanschie.quickskript.core.util.literal.Slot;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a slot of a player on the hotbar. This does not include the off-hand slot.
 *
 * @since 0.1.0
 */
public class HotbarSlot implements Slot {

    /**
     * The inventory to which this slot belongs to.
     */
    @NotNull
    private final Inventory inventory;

    /**
     * The index of the slot
     */
    private final int index;

    /**
     * Creates a new hotbar slot for a given inventory. The slot index is between 0-8, from left to right.
     *
     * @param inventory the inventory in which the slot resides
     * @param index the index of the slot on the hotbar
     * @throws IllegalArgumentException when the slot is outside the range of the hotbar
     */
    public HotbarSlot(@NotNull Inventory inventory, int index) {
        if (index < 0 || index > 9) {
            throw new IllegalArgumentException("Index must be between 0-8, but is '" + index + "'");
        }

        this.inventory = inventory;
        this.index = index;
    }

    @Contract(pure = true)
    @Override
    public boolean isEmpty() {
        ItemStack item = this.inventory.getItem(getIndex());

        return item == null || item.getType() == Material.AIR;
    }

    /**
     * Gets the index of this hotbar slot.
     *
     * @return the index
     * @since 0.1.0
     */
    @Contract(pure = true)
    public int getIndex() {
        return this.index;
    }
}
