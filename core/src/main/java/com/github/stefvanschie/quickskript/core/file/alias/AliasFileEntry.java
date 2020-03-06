package com.github.stefvanschie.quickskript.core.file.alias;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
    private String entry;

    /**
     * Creates a new entry for an alias file.
     *
     * @param entry the entry
     * @since 0.1.0
     */
    public AliasFileEntry(@NotNull String entry) {
        this.entry = entry;
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
}
