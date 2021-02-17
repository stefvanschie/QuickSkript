package com.github.stefvanschie.quickskript.core.util.registry;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * A registry for inventory types
 *
 * @since 0.1.0
 */
public class InventoryTypeRegistry {

    /**
     * The entries of this registry
     */
    private Map<String, Entry> entries = new HashMap<>();

    /**
     * Creates a new inventory type registry initialized with the default inventory types.
     *
     * @since 0.1.0
     */
    public InventoryTypeRegistry() {
        addDefaultInventoryTypes();
    }

    /**
     * Adds the specified entry to the registry
     *
     * @param entry the entry to add
     * @since 0.1.0
     */
    public void addEntry(@NotNull Entry entry) {
        entries.put(entry.getName(), entry);
    }

    /**
     * Gets the inventory type by the given name or null if no such inventory type exists.
     *
     * @param name the name of the inventory type
     * @return the inventory type
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public Entry byName(@NotNull String name) {
        return entries.get(name);
    }

    /**
     * Adds the default inventory types to the registry
     *
     * @since 0.1.0
     */
    private void addDefaultInventoryTypes() {
        addEntry(new Entry("anvil inventory"));
        addEntry(new Entry("barrel inventory"));
        addEntry(new Entry("beacon inventory"));
        addEntry(new Entry("blast furnace inventory"));
        addEntry(new Entry("brewing stand inventory"));
        addEntry(new Entry("cartography table inventory"));
        addEntry(new Entry("chest inventory"));
        addEntry(new Entry("crafting table inventory"));
        addEntry(new Entry("creative inventory"));
        addEntry(new Entry("dispenser inventory"));
        addEntry(new Entry("dropper inventory"));
        addEntry(new Entry("enchanting table inventory"));
        addEntry(new Entry("ender chest inventory"));
        addEntry(new Entry("furnace inventory"));
        addEntry(new Entry("grindstone inventory"));
        addEntry(new Entry("hopper inventory"));
        addEntry(new Entry("lectern inventory"));
        addEntry(new Entry("loom inventory"));
        addEntry(new Entry("merchant inventory"));
        addEntry(new Entry("player inventory"));
        addEntry(new Entry("shulker box inventory"));
        addEntry(new Entry("smoker inventory"));
        addEntry(new Entry("stonecutter inventory"));
        addEntry(new Entry("workbench inventory"));
    }

    /**
     * An entry for the inventory type registry
     *
     * @since 0.1.0
     */
    public static class Entry {

        /**
         * The name of the entry
         */
        @NotNull
        private final String name;

        /**
         * Creates a new entry with the specified name
         *
         * @param name the name of the entry
         * @since 0.1.0
         */
        public Entry(@NotNull String name) {
            this.name = name;
        }

        /**
         * Gets the name of this entry
         *
         * @return the name
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public String getName() {
            return name;
        }
    }
}
