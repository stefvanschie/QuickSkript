package com.github.stefvanschie.quickskript.core.psi.expression.util;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An interface indicating that something can be removed from this expression.
 *
 * @since 0.1.0
 */
public interface Removable {

    /**
     * Removes the result of the element from this expression
     *
     * @param context the context in which this method is called
     * @param object the object to remove
     * @since 0.1.0
     */
    void remove(@Nullable Context context, @NotNull PsiElement<?> object);

}
