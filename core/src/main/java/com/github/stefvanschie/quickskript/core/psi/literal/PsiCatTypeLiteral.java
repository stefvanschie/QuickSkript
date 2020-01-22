package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.util.literal.CatType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

/**
 * Gets a cat type
 *
 * @since 0.1.0
 */
public class PsiCatTypeLiteral extends PsiPrecomputedHolder<CatType> {

    /**
     * Creates a new element with the given line number
     *
     * @param catType the type of cat this literal represents
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiCatTypeLiteral(@NotNull CatType catType, int lineNumber) {
        super(catType, lineNumber);
    }

    /**
     * A factory for creating {@link PsiCatTypeLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * This gets called upon parsing
         *
         * @param text the text to parse
         * @param lineNumber the line number
         * @return the literal, or null to indicate failure
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiCatTypeLiteral parse(@NotNull String text, int lineNumber) {
            if (!text.startsWith("cat types.")) {
                return null;
            }

            CatType catType;

            try {
                catType = CatType.valueOf(text.substring("cat types.".length()).toUpperCase(Locale.getDefault()));
            } catch (IllegalArgumentException exception) {
                return null;
            }

            return create(catType, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param catType the type of cat this literal represents
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiCatTypeLiteral create(@NotNull CatType catType, int lineNumber) {
            return new PsiCatTypeLiteral(catType, lineNumber);
        }
    }
}
