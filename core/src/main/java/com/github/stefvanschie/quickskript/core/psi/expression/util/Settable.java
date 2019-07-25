package com.github.stefvanschie.quickskript.core.psi.expression.util;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

/**
 * An interface indicating that something can be set to this expression.
 *
 * @since 0.1.0
 */
public interface Settable {

    /**
     * Sets the result of the element to this expression
     *
     * @param object the object to set
     * @since 0.1.0
     */
    void set(@NotNull PsiElement<?> object);
}