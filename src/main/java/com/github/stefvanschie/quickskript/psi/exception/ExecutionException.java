package com.github.stefvanschie.quickskript.psi.exception;

/**
 * An exception thrown when the psi was unable to execute properly.
 * This {@link RuntimeException} extends {@link ParseException} because
 * parsing can include execution in the form of pre computation which can can
 * lead to {@link ExecutionException}s being thrown.
 *
 * @since 0.1.0
 */
public class ExecutionException extends ParseException {

    /**
     * {@inheritDoc}
     */
    public ExecutionException(String message) {
        super(message);
    }
}
