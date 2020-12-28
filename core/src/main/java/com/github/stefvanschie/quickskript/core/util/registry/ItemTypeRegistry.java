package com.github.stefvanschie.quickskript.core.util.registry;

import com.github.stefvanschie.quickskript.core.file.alias.manager.AliasFileManager;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
     * The different categories mapped to their entries
     */
    private final Map<SkriptPattern, Set<Entry>> categories = new HashMap<>();

    /**
     * A map from an unrolled string to the corresponding entry
     */
    private final Map<String, Entry> unrolledEntries = new HashMap<>();

    /**
     * A map from an unrolled category to the corresponding entries
     */
    private final Map<String, Set<Entry>> unrolledCategories = new HashMap<>();

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

        for (String unrolledPatternMatch : entry.unrolledPatternMatches) {
            unrolledEntries.put(unrolledPatternMatch, entry);
        }

        for (SkriptPattern category : entry.getCategories()) {
            categories.computeIfAbsent(category, c -> new HashSet<>()).add(entry);
        }

        for (String unrolledCategory : entry.unrolledCategories) {
            unrolledCategories.computeIfAbsent(unrolledCategory, c -> new HashSet<>()).add(entry);
        }
    }

    /**
     * Gets an entry by a name. If no entry exists by the given name, this returns null.
     *
     * @param name the name
     * @return the entry or null
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public Entry getEntryByName(@NotNull String name) {
        return unrolledEntries.get(name);
    }

    /**
     * Gets all entries belonging to the given category. If no entries exist by the given category, this returns null.
     *
     * @param category the category to look by
     * @return the entries this category corresponds to
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public Set<Entry> getEntriesByCategory(@NotNull String category) {
        return unrolledCategories.get(category);
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
        return Collections.unmodifiableCollection(this.entries);
    }

    /**
     * Gets all categories and their entries. Entries which do not belong to a category are not included in this. The
     * returned map is unmodifiable.
     *
     * @return the categories
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Map<SkriptPattern, Set<Entry>> getCategories() {
        return Collections.unmodifiableMap(this.categories);
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
         * The possible matches this pattern has when all possible matches are unrolled.
         */
        @NotNull
        private final Collection<String> unrolledPatternMatches;

        /**
         * The categories this item type belongs to
         */
        @NotNull
        private final Collection<SkriptPattern> categories = new HashSet<>();

        /**
         * The unrolled categories for this entry
         */
        @NotNull
        private final Collection<String> unrolledCategories = new HashSet<>();

        /**
         * Creates a new entry with the specified pattern
         *
         * @param pattern the pattern for the entry
         * @since 0.1.0
         */
        public Entry(@NotNull SkriptPattern pattern) {
            this.pattern = pattern;
            this.unrolledPatternMatches = this.pattern.unrollFully();
        }

        /**
         * Adds the specified category to the collection of categories
         *
         * @param category the category to add
         * @since 0.1.0
         */
        public void addCategory(@NotNull SkriptPattern category) {
            this.categories.add(category);
            this.unrolledCategories.addAll(category.unrollFully());
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
