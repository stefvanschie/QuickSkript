package com.github.stefvanschie.quickskript.core.pattern.exception;

/**
 * An exception thrown when an operation is to be performed on a skript pattern containing groups that don't support
 * this operation.
 *
 * @since 0.1.0
 */
public class SkriptPatternInvalidGroupException extends RuntimeException {

    public SkriptPatternInvalidGroupException(String message) {
        super(message);
    }
}
