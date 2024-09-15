package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks whether the egg in a player egg throw event will hatch.
 *
 * @since 0.1.0
 */
public class PsiEggWillHatchCondition extends PsiElement<Boolean> {

    /**
     * If false, the result is inverted.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param positive if false, the result is inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiEggWillHatchCondition(boolean positive, int lineNumber) {
        super(lineNumber);

        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiEggWillHatchCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching positive {@link PsiEggWillHatchCondition}s
         */
        @NotNull
        private final SkriptPattern positivePattern = SkriptPattern.parse("[the] egg will hatch");

        /**
         * The pattern for matching negative {@link PsiEggWillHatchCondition}s
         */
        @NotNull
        private final SkriptPattern negativePattern = SkriptPattern.parse("[the] egg (will not|won't) hatch");

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        public PsiEggWillHatchCondition parsePositive(int lineNumber) {
            return create(true, lineNumber);
        }

        /**
         * Parses the {@link #negativePattern} and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        public PsiEggWillHatchCondition parseNegative(int lineNumber) {
            return create(false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiEggWillHatchCondition create(boolean positive, int lineNumber) {
            return new PsiEggWillHatchCondition(positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
