package com.github.stefvanschie.quickskript.core.psi.expression.util;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

/**
 * Indicates that a {@link #removeAll(PsiElement)} operation can be performed on the expression. Automatically means
 * that the normal {@link #remove(PsiElement)} operation can also be done.
 *
 * @since 0.1.0
 */
public interface RemoveAllable extends Removable {

    /**
     * Remove all of the result of the element from this expression
     *
     * @param object the object to removed
     * @since 0.1.0
     */
    void removeAll(@NotNull PsiElement<?> object);
}
