package com.github.stefvanschie.quickskript.core.psi.expression.util;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

/**
 * An interface indicating that something can be added to this expression.
 *
 * @since 0.1.0
 */
public interface Addable {

    /**
     * Adds the specified element to this expression
     *
     * @param object the object to add
     * @since 0.1.0
     */
    void add(@NotNull PsiElement<?> object);
}
