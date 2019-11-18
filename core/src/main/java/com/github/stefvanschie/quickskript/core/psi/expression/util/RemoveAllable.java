package com.github.stefvanschie.quickskript.core.psi.expression.util;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Indicates that a {@link #removeAll(Context, PsiElement)} operation can be performed on the expression.
 *
 * @since 0.1.0
 */
public interface RemoveAllable extends Removable {

    /**
     * Remove all of the result of the element from this expression
     *
     * @param context the context this code is being executed in, or null during pre computation
     * @param object the object to removed
     * @since 0.1.0
     */
    default void removeAll(@Nullable Context context, @NotNull PsiElement<?> object) {
        throw new UnsupportedOperationException("Cannot change expression without implementation.");
    }
}
