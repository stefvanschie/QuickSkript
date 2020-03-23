package com.github.stefvanschie.quickskript.core.file.alias.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Thrown when an undefined variation is found in an alias file.
 *
 * @since 0.1.0
 */
public class UndefinedAliasFileVariation extends RuntimeException {

    /**
     * Creates a new undefined variation exception with the provided cause
     *
     * @param cause the cause of this exception
     * @since 0.1.0
     */
    public UndefinedAliasFileVariation(@NotNull String cause) {
        super(cause);
    }
}
