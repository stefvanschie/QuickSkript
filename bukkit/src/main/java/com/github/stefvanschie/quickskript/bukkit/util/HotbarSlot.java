package com.github.stefvanschie.quickskript.bukkit.util;

import com.github.stefvanschie.quickskript.core.util.literal.Slot;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a slot of a player on the hotbar. This does not include the off-hand slot.
 *
 * @since 0.1.0
 */
public class HotbarSlot implements Slot {

    /**
     * The index of the slot
     */
    private final int index;

    /**
     * Creates a new hotbar slot for a given player. The slot index is between 0-8, from left to right.
     *
     * @param player the player whose slot this is
     * @param index the index of the slot on the hotbar
     * @throws IllegalArgumentException when the slot is outside the range of the hotbar
     */
    public HotbarSlot(@NotNull Player player, int index) {
        if (index < 0 || index > 9) {
            throw new IllegalArgumentException("Index must be between 0-8, but is '" + index + "'");
        }

        this.index = index;
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
