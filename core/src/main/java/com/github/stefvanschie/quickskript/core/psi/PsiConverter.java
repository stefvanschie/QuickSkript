package com.github.stefvanschie.quickskript.core.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An abstract converter interface which converts values from one type to a psi element.
 *
 * @param <T> the result of this conversion
 */
public interface PsiConverter<T extends PsiElement<?>> {

    /**
     * Converts the specified object to the result type, may return null if conversion isn't possible.
     *
     * @param object the object to convert
     * @param lineNumber the line number of the element that computed this object
     * @return the converted object
     * @since 0.1.0
     */
    @Nullable
    T convert(@NotNull Object object, int lineNumber);
}
