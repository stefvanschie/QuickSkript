package com.github.stefvanschie.quickskript.core.psi.util.parsing.exception;

import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;

/**
 * Thrown when an illegal amount of {@link Fallback} annotations have been found.
 *
 * @since 0.1.0
 */
public class IllegalFallbackAnnotationAmount extends RuntimeException {

    /**
     * {@inheritDoc}
     */
    public IllegalFallbackAnnotationAmount(String message) {
        super(message);
    }
}
