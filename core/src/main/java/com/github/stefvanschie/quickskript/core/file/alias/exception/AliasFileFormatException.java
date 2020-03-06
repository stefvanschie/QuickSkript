package com.github.stefvanschie.quickskript.core.file.alias.exception;

import org.jetbrains.annotations.NotNull;

/**
 * An exception indicating that an alias file is incorrectly formatted.
 *
 * @since 0.1.0
 */
public class AliasFileFormatException extends RuntimeException {

    /**
     * Creates this exception with the given cause as the reason for why this exception occured.
     *
     * @param cause the cause of this exception.
     * @since 0.1.0
     */
    public AliasFileFormatException(@NotNull String cause) {
        super(cause);
    }
}
