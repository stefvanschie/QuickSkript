package com.github.stefvanschie.quickskript.core.psi.expression.util;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An interface indicating that something can be set to this expression.
 *
 * @since 0.1.0
 */
public interface Settable {

    /**
     * Sets the result of the element to this expression
     *
     * @param context the context this code is being executed in, or null during pre computation
     * @param object the object to set
     * @since 0.1.0
     */
    default void set(@Nullable Context context, @NotNull PsiElement<?> object) {
        throw new UnsupportedOperationException("Cannot change expression without implementation.");
    }
}
