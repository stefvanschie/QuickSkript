package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.util.literal.Color;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets a color
 *
 * @since 0.1.0
 */
public class PsiColorLiteral extends PsiPrecomputedHolder<Color> {

    /**
     * Creates a new element with the given line number
     *
     * @param color the color
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiColorLiteral(@NotNull Color color, int lineNumber) {
        super(color, lineNumber);
    }

    /**
     * A factory for creating {@link PsiColorLiteral}s
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
        public PsiColorLiteral parse(@NotNull String text, int lineNumber) {
            for (Color color : Color.values()) {
                for (String name : color.getNames()) {
                    if (name.equalsIgnoreCase(text)) {
                        return create(color, lineNumber);
                    }
                }
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param color the color
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiColorLiteral create(@NotNull Color color, int lineNumber) {
            return new PsiColorLiteral(color, lineNumber);
        }
    }
}
