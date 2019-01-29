package com.github.stefvanschie.quickskript.core.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An abstract factory interface for parsing psi elements. Every element needs at least one factory, but may have more.
 *
 * @param <T> the type of psi element this will return
 * @since 0.1.0
 */
public interface PsiElementFactory<T extends PsiElement<?>> {

    /**
     * Tries to parse the given text for this specified factory.
     *
     * @param text the text to be parsed
     * @param lineNumber the line number of the element we're currently parsing
     * @return the element created, or null if the element could not be created
     * @since 0.1.0
     */
    @Nullable
    T tryParse(@NotNull String text, int lineNumber);
}
