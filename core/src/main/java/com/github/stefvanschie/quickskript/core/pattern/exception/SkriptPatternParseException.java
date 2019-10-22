package com.github.stefvanschie.quickskript.core.pattern.exception;

/**
 * An exception thrown if a skript pattern is invalid.
 *
 * @since 0.1.0
 */
public class SkriptPatternParseException extends RuntimeException {

    public SkriptPatternParseException(String message) {
        super(message);
    }
}
