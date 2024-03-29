package com.github.stefvanschie.quickskript.core.file.alias;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * An entry for the given file. This represents one entry in the file.
 *
 * @since 0.1.0
 */
public class AliasFileEntry {

    /**
     * The textual representation of this entry.
     */
    @NotNull
    private final String entry;

    /**
     * The namespaced key representing which item this entry represents or null if this entry does not have a key
     */
    @Nullable
    private final String key;

    /**
     * The categories this entry belongs to.
     */
    @NotNull
    private final Collection<String> categories = new HashSet<>();

    /**
     * Creates a new entry for an alias file.
     *
     * @param entry the entry
     * @param key the key
     * @since 0.1.0
     */
    public AliasFileEntry(@NotNull String entry, @Nullable String key) {
        this.entry = entry;
        this.key = key;
    }

    /**
     * Adds a category to this entry
     *
     * @param category the category to add
     * @since 0.1.0
     */
    public void addCategory(@NotNull String category) {
        this.categories.add(category);
    }

    /**
     * Get all the categories this entry belongs to. The returned collection is unmodifiable, but not safe for
     * concurrent access.
     *
     * @return the categories
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Collection<String> getCategories() {
        return Collections.unmodifiableCollection(categories);
    }

    /**
     * Gets the entry of this object
     *
     * @return the entry
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public String getEntry() {
        return entry;
    }

    /**
     * Gets the key of this entry
     *
     * @return the key
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public String getKey() {
        return key;
    }
}
