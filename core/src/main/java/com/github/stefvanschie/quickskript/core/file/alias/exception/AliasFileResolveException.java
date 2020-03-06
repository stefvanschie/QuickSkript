package com.github.stefvanschie.quickskript.core.file.alias.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Thrown when an issue occurs while resolving an alias file.
 *
 * @since 0.1.0
 */
public class AliasFileResolveException extends RuntimeException {

    /**
     * Creates a new resolve exception with the provided cause
     *
     * @param cause the cause of this exception
     * @since 0.1.0
     */
    public AliasFileResolveException(@NotNull String cause) {
        super(cause);
    }
}
