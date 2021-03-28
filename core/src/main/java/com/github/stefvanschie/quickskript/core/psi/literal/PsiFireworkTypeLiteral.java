package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.util.Type;
import com.github.stefvanschie.quickskript.core.util.literal.FireworkType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets a firework type
 *
 * @since 0.1.0
 */
public class PsiFireworkTypeLiteral extends PsiPrecomputedHolder<FireworkType> {

    /**
     * Creates a new element with the given line number
     *
     * @param fireworkType the firework type
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiFireworkTypeLiteral(@NotNull FireworkType fireworkType, int lineNumber) {
        super(fireworkType, lineNumber);
    }

    /**
     * A factory for creating {@link PsiFireworkTypeLiteral}s
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
        public PsiFireworkTypeLiteral parse(@NotNull String text, int lineNumber) {
            FireworkType fireworkType = FireworkType.byName(text.toLowerCase());

            if (fireworkType == null) {
                return null;
            }

            return create(fireworkType, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param fireworkType the firework type
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiFireworkTypeLiteral create(@NotNull FireworkType fireworkType, int lineNumber) {
            return new PsiFireworkTypeLiteral(fireworkType, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.FIREWORK_TYPE;
        }
    }
}
