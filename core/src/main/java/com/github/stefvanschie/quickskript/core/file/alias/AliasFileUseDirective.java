package com.github.stefvanschie.quickskript.core.file.alias;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@code :use} directive in an alias file that refers to another alias file this file depends on.
 *
 * @since 0.1.0
 */
public class AliasFileUseDirective {

    /**
     * Represents the textual representation of the relative file path that refers to the dependency for this alias
     * file.
     */
    @NotNull
    private String filePath;

    /**
     * Creates a new use directive with the given file path.
     *
     * @since 0.1.0
     */
    public AliasFileUseDirective(@NotNull String filePath) {
        this.filePath = filePath;
    }

    /**
     * Gets the textual representation of the relative file path of the dependency this directive refers to.
     *
     * @return the relative file path
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public String getFilePath() {
        return filePath;
    }
}
