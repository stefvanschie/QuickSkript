package com.github.stefvanschie.quickskript.core.util.literal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents possible inventory actions
 *
 * @since 0.1.0
 */
public enum InventoryAction {

    /**
     * Represents cloning a stack
     *
     * @since 0.1.0
     */
    CLONE_STACK,

    /**
     * Represents collecting items and putting them on the cursor
     *
     * @since 0.1.0
     */
    COLLECT_TO_CURSOR,

    /**
     * Represents dropping everything from the cursor
     *
     * @since 0.1.0
     */
    DROP_ALL_FROM_CURSOR,

    /**
     * Represents dropping everything from a slot
     *
     * @since 0.1.0
     */
    DROP_ALL_FROM_SLOT,

    /**
     * Represents dropping one item from the cursor
     *
     * @since 0.1.0
     */
    DROP_ONE_FROM_CURSOR,

    /**
     * Represents dropping one item from a slot
     *
     * @since 0.1.0
     */
    DROP_ONE_FROM_SLOT,

    /**
     * Represents moving items from a hotbar and re-adding them
     *
     * @since 0.1.0
     */
    HOTBAR_MOVE_AND_READD,

    /**
     * Represents instantly moving items between slots
     *
     * @since 0.1.0
     */
    INSTANT_MOVE,

    /**
     * Represents doing nothing
     *
     * @since 0.1.0
     */
    NOTHING,

    /**
     * Represents picking up all items
     *
     * @since 0.1.0
     */
    PICKUP_ALL,

    /**
     * Represents picking up half of the items
     *
     * @since 0.1.0
     */
    PICKUP_HALF,

    /**
     * Represents picking up one item
     *
     * @since 0.1.0
     */
    PICKUP_ONE_ITEM,

    /**
     * Represents picking up some items
     *
     * @since 0.1.0
     */
    PICKUP_SOME,

    /**
     * Represents placing up all items
     *
     * @since 0.1.0
     */
    PLACE_ALL,

    /**
     * Represents placing one item
     *
     * @since 0.1.0
     */
    PLACE_ONE,

    /**
     * Represents placing some items
     *
     * @since 0.1.0
     */
    PLACE_SOME,

    /**
     * Represents swapping items with the cursor
     *
     * @since 0.1.0
     */
    SWAP_WITH_CURSOR,

    /**
     * Represents swapping items with the hotbar
     *
     * @since 0.1.0
     */
    SWAP_WITH_HOTBAR,

    /**
     * Represents an unknown action
     *
     * @since 0.1.0
     */
    UNKNOWN;

    /**
     * The inventory actions by their name
     */
    @NotNull
    private static final Map<String, InventoryAction> ENTRIES = new HashMap<>();

    /**
     * Gets the inventory action by the given name or null if no such inventory action exists.
     *
     * @param name the name of the inventory action
     * @return the inventory action or null
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public static InventoryAction byName(@NotNull String name) {
        return ENTRIES.get(name);
    }

    static {
        for (InventoryAction inventoryAction : InventoryAction.values()) {
            ENTRIES.put(inventoryAction.name().replace('_', ' ').toLowerCase(), inventoryAction);
        }
    }
}
