package com.github.stefvanschie.quickskript.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An abstract factory interface for parsing psi elements. Every element needs at least one factory, but may have more.
 *
 * @param <T> the type of psi element this will return
 * @since 0.1.0
 */
public interface PsiFactory<T extends PsiElement<?>> {

    /**
     * Tries to parse the given text for this specified factory.
     *
     * @param text the text to be parsed
     * @return the element created, or null if the element could not be created
     * @since 0.1.0
     */
    @Nullable
    T parse(@NotNull String text);
}
