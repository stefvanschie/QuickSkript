package com.github.stefvanschie.quickskript.core.psi;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * An abstract factory interface for parsing psi sections.
 * Every section needs at least one factory, but may have more.
 *
 * @param <T> the type of psi section this will return
 * @since 0.1.0
 */
public interface PsiSectionFactory<T extends PsiSection> {
    /**
     * Tries to parse the given text for this specified factory.
     *
     * @param skriptLoader the skript loader to parse with
     * @param text the text to be parsed
     * @param elementsSupplier the action which parses the contained elements on demand
     * @param lineNumber the line number of the element we're currently parsing
     * @return the element created, or null if the element could not be created
     * @since 0.1.0
     */
    @Nullable
    T tryParse(@NotNull SkriptLoader skriptLoader, @NotNull String text,
        @NotNull Supplier<PsiElement<?>[]> elementsSupplier, int lineNumber);
}
