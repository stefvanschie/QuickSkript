package com.github.stefvanschie.quickskript.core.psi.expression.util;

import com.github.stefvanschie.quickskript.core.context.Context;
import org.jetbrains.annotations.Nullable;

/**
 * An interface indicating that the object this element holds can be deleted
 *
 * @since 0.1.0
 */
public interface Deletable {

    /**
     * Deletes the expression's result
     *
     * @param context the context this code is being executed in, or null during pre computation
     * @since 0.1.0
     */
    default void delete(@Nullable Context context) {
        throw new UnsupportedOperationException("Cannot change expression without implementation.");
    }
}
