package com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Thrown when a value inside an annotation used for parsing is invalid in some way.
 *
 * @since 0.1.0
 */
public class ParsingAnnotationInvalidValueException extends RuntimeException {

    public ParsingAnnotationInvalidValueException(@NotNull String message) {
        super(message);
    }
}
