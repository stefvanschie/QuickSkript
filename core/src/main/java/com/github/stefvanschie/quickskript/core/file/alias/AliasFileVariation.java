package com.github.stefvanschie.quickskript.core.file.alias;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * Represents possible variations that may be used inside an alias entry.
 *
 * @since 0.1.0
 */
public class AliasFileVariation {

    /**
     * The entries in this variation. This represents all possible patterns that may be used to represent this
     * variation, mapped to their attribute value
     */
    @NotNull
    private final Map<String, String> entries;

    /**
     * The name of this variation
     */
    @NotNull
    private String name;

    /**
     * The full name of this variation. This is the same as {@link #name}, but with a '{' added to the start and a '}'
     * added to the end of the name.
     */
    @NotNull
    private String fullName;

    /**
     * This will be true if this variation is optional and false otherwise.
     */
    private boolean optional;

    /**
     * Creates a new variation with the given entries.
     *
     * @param entries the entries of this variation
     * @param name the name of this variation
     * @param optional whether this variation is optional or not
     * @since 0.1.0
     */
    public AliasFileVariation(@NotNull Map<String, String> entries, @NotNull String name, boolean optional) {
        this.entries = entries;
        this.name = name;
        this.optional = optional;
        this.fullName = '{' + name + '}';
    }

    /**
     * Gets all possible entries of this variation
     *
     * @return all variations
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Map<String, String> getEntries() {
        return entries;
    }

    /**
     * Gets the full name of this variation. This is the name of the variation with curly braces surrounding it.
     *
     * @return the full name
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public String getFullName() {
        return fullName;
    }

    /**
     * Gets the name of this variations
     *
     * @return the name of this variation
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public String getName() {
        return name;
    }

    /**
     * Gets whether this variation is optional
     *
     * @return whether this is optional
     * @since 0.1.0
     */
    @Contract(pure = true)
    public boolean isOptional() {
        return optional;
    }
}
