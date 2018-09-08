package com.github.stefvanschie.quickskript.psi.exception;

/**
 * An exception thrown when the parser was unable to execute properly.
 *
 * @since 0.1.0
 */
public class ParseException extends RuntimeException {

    /**
     * Stores the line number this error occurred at
     */
    private int lineNumber;

    /**
     * {@inheritDoc}
     */
    public ParseException(String message, int lineNumber) {
        super(message);

        this.lineNumber = lineNumber;
    }

    /**
     * Gets the line number this error occured at
     *
     * @return the line number
     * @since 0.1.0
     */
    public int getLineNumber() {
        return lineNumber;
    }
}
