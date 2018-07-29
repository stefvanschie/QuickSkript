package com.github.stefvanschie.quickskript.psi.exception;

/**
 * An exception thrown when the parser was unable to execute properly.
 *
 * @since 0.1.0
 */
public class ParseException extends RuntimeException {

    /**
     * {@inheritDoc}
     */
    public ParseException(String message) {
        super(message);
    }
}
