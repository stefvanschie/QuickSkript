package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.util.literal.InputKey;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a literal input key.
 *
 * @since 0.1.0
 */
public class PsiInputKeyLiteral extends PsiPrecomputedHolder<InputKey> {

    /**
     * Creates a new psi element which holds a precomputed input key
     *
     * @param inputKey the input key this psi is wrapping
     * @param lineNumber the line number of this element
     * @since 0.1.0
     */
    private PsiInputKeyLiteral(@NotNull InputKey inputKey, int lineNumber) {
        super(inputKey, lineNumber);
    }

    /**
     * A factory to create instances of {@link PsiInputKeyLiteral}.
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
        public PsiInputKeyLiteral parse(@NotNull String text, int lineNumber) {
            InputKey inputKey = InputKey.byName(text.toLowerCase());

            if (inputKey == null) {
                return null;
            }

            return create(inputKey, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param inputKey the input key
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiInputKeyLiteral create(@NotNull InputKey inputKey, int lineNumber) {
            return new PsiInputKeyLiteral(inputKey, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public String getType() {
            return "input key";
        }
    }
}
