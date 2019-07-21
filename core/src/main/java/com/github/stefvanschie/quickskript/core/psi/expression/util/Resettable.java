package com.github.stefvanschie.quickskript.core.psi.expression.util;

/**
 * An interface indicating that something can be reset from this expression.
 *
 * @since 0.1.0
 */
public interface Resettable {

    /**
     * Resets the expression's result
     *
     * @since 0.1.0
     */
    void reset();
}
