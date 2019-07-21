package com.github.stefvanschie.quickskript.core.psi.expression.util;

/**
 * An interface indicating that the object this element holds can be deleted
 *
 * @since 0.1.0
 */
public interface Deletable {

    /**
     * Deletes the expression's result
     *
     * @since 0.1.0
     */
    void delete();
}
