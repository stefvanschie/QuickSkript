package com.github.stefvanschie.quickskript.psi.exception;

/**
 * An exception thrown when the psi was unable to execute properly.
 *
 * @since 0.1.0
 */
public class ExecutionException extends RuntimeException {

    /**
     * {@inheritDoc}
     */
    public ExecutionException(String message) {
        super(message);
    }
}
