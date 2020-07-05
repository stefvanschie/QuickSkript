package com.github.stefvanschie.quickskript.core.util.registry;

import com.github.stefvanschie.quickskript.core.file.alias.manager.AliasFileManager;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;

/**
 * A registry that contains all item types
 *
 * @since 0.1.0
 */
public class ItemTypeRegistry {

    /**
     * The entries of this registry
     */
    private final Set<Entry> entries = new HashSet<>();

    /**
     * Creates a new item type registry and adds the default item types to it
     *
     * @since 0.1.0
     */
    public ItemTypeRegistry() {
        addDefaultItemTypes();
    }

    /**
     * Adds the specified entry to this registry
     *
     * @param entry the entry to add
     * @since 0.1.0
     */
    public void addEntry(@NotNull Entry entry) {
        entries.add(entry);
    }

    /**
     * Gets all the entries in this registry
     *
     * @return all entries
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Collection<Entry> getEntries() {
        return Collections.unmodifiableSet(entries);
    }

    /**
     * Adds all the default item types to this registry
     *
     * @since 0.1.0
     */
    private void addDefaultItemTypes() {
        var manager = new AliasFileManager();

        try {
            manager.read(getClass().getResource("/registry-data/util/global.alias"), "util/global.alias");
            manager.read(getClass().getResource("/registry-data/brewing.alias"), "brewing.alias");
            manager.read(getClass().getResource("/registry-data/building.alias"), "building.alias");
            manager.read(getClass().getResource("/registry-data/combat.alias"), "combat.alias");
            manager.read(getClass().getResource("/registry-data/decoration.alias"), "decoration.alias");
            manager.read(getClass().getResource("/registry-data/food.alias"), "food.alias");
            manager.read(getClass().getResource("/registry-data/miscellaneous.alias"),
                "miscellaneous.alias");
            manager.read(getClass().getResource("/registry-data/other.alias"), "other.alias");
            manager.read(getClass().getResource("/registry-data/redstone.alias"), "redstone.alias");
            manager.read(getClass().getResource("/registry-data/tools.alias"), "tools.alias");
            manager.read(getClass().getResource("/registry-data/transportation.alias"),
                "transportation.alias");
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        manager.resolveAll().forEach(this::addEntry);
    }

    /**
     * An entry for the registry
     *
     * @since 0.1.0
     */
    public static class Entry {

        /**
         * The pattern for this entry.
         */
        @NotNull
        private final SkriptPattern pattern;

        /**
         * The categories this item type belongs to
         */
        @NotNull
        private final Collection<SkriptPattern> categories = new HashSet<>();

        /**
         * Creates a new entry with the specified pattern
         *
         * @param pattern the pattern for the entry
         * @since 0.1.0
         */
        public Entry(@NotNull SkriptPattern pattern) {
            this.pattern = pattern;
        }

        /**
         * Adds the specified category to the collection of categories
         *
         * @param category the category to add
         * @since 0.1.0
         */
        public void addCategory(@NotNull SkriptPattern category) {
            this.categories.add(category);
        }

        /**
         * Gets all categories that this item type belongs to. The returned collection is unmodifiable, for mutating the
         * categories, see {@link #addCategory(SkriptPattern)}. The returned collection is not safe for concurrent
         * access.
         *
         * @return all categories of this item type
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public Collection<SkriptPattern> getCategories() {
            return Collections.unmodifiableCollection(categories);
        }

        /**
         * Get the pattern for the entry.
         *
         * @return the pattern
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public SkriptPattern getPattern() {
            return pattern;
        }
    }
}
