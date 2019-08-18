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
     * @param context the context in which this method is called
     * @since 0.1.0
     */
    void delete(@Nullable Context context);
}
