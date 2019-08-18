package com.github.stefvanschie.quickskript.core.psi.expression.util;

import com.github.stefvanschie.quickskript.core.context.Context;
import org.jetbrains.annotations.Nullable;

/**
 * An interface indicating that something can be reset from this expression.
 *
 * @since 0.1.0
 */
public interface Resettable {

    /**
     * Resets the expression's result
     *
     * @param context the context in which this method is called
     * @since 0.1.0
     */
    void reset(@Nullable Context context);
}
