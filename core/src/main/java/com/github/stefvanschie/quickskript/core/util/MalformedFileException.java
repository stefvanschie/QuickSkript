package com.github.stefvanschie.quickskript.core.util;

import org.jetbrains.annotations.NotNull;

/**
 * Thrown when a file could be read, but its format was incorrect.
 *
 * @since 0.1.0
 */
public class MalformedFileException extends RuntimeException {

    /**
     * Creates a malformed file exception with the given cause.
     *
     * @param cause the cause of this exception
     * @since 0.1.0
     */
    public MalformedFileException(@NotNull String cause) {
        super(cause);
    }
}
