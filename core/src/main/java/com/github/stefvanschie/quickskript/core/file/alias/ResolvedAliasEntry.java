package com.github.stefvanschie.quickskript.core.file.alias;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * An alias entry that has been fully resolved
 *
 * @since 0.1.0
 */
public class ResolvedAliasEntry {

    /**
     * The pattern to identify this alias entry
     */
    @NotNull
    private final SkriptPattern pattern;

    /**
     * The key belonging to this entry
     */
    @Nullable
    private final String key;

    /**
     * The categories this entry belongs to
     */
    @NotNull
    private final Collection<? extends SkriptPattern> categories;

    /**
     * The attributes of the key
     */
    @NotNull
    private final Collection<? extends String> attributes;

    /**
     * Creates a resolves alias entry with parsed information.
     *
     * @param pattern the pattern for this entry
     * @param key the key identifying this entry
     * @param categories the categories belonging to this entry
     * @param attributes the attributes for this entry
     * @since 0.1.0
     */
    public ResolvedAliasEntry(@NotNull SkriptPattern pattern, @Nullable String key,
        @NotNull Collection<? extends SkriptPattern> categories,
        @NotNull Collection<? extends String> attributes) {
        this.pattern = pattern;
        this.key = key;
        this.categories = categories;
        this.attributes = attributes;
    }

    /**
     * Gets the pattern this entry corresponds to.
     *
     * @return the pattern
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public SkriptPattern getPattern() {
        return pattern;
    }

    /**
     * Gets the key that identifies the item this entry corresponds to.
     *
     * @return the key
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public String getKey() {
        return key;
    }

    /**
     * Gets the attributes that belong to the key. The returned map is unmodifiable.
     *
     * @return the attributes
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Collection<? extends String> getAttributes() {
        return Collections.unmodifiableCollection(attributes);
    }

    /**
     * Gets the categories that this entry is part of. The returned collection is unmodifiable.
     *
     * @return the categories
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Collection<? extends SkriptPattern> getCategories() {
        return Collections.unmodifiableCollection(categories);
    }
}
