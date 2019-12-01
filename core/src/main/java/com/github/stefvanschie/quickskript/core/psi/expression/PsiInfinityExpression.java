package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the special numbers positive or negative infinity.
 *
 * @since 0.1.0
 */
public class PsiInfinityExpression extends PsiElement<Double> {

    /**
     * The sign for the number
     */
    private Sign sign;

    /**
     * Creates a new element with the given line number
     *
     * @param sign the sign of the infinity
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiInfinityExpression(@NotNull Sign sign, int lineNumber) {
        super(lineNumber);

        this.sign = sign;

        preComputed = executeImpl(null);

        this.sign = null;
    }

    @Nullable
    @Override
    protected Double executeImpl(@Nullable Context context) {
        if (sign == Sign.POSITIVE) {
            return Double.POSITIVE_INFINITY;
        }

        if (sign == Sign.NEGATIVE) {
            return Double.NEGATIVE_INFINITY;
        }

        throw new ExecutionException(new IllegalStateException("Unknown sign for infinity"), lineNumber);
    }

    /**
     * A factory for creating {@link PsiInfinityExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching positive {@link PsiInfinityExpression}s
         */
        @NotNull
        private SkriptPattern[] positivePatterns = SkriptPattern.parse(
            "(infinity|\u221E) value", //\u221E is the character for infinity
            "value of (infinity|\u221E)"
        );

        /**
         * The pattern for matching negative {@link PsiInfinityExpression}s
         */
        @NotNull
        private SkriptPattern[] negativePatterns = SkriptPattern.parse(
            "(-|minus)(infinity|\u221E) value", //\u221E is the character for infinity
            "value of (-|minus)(infinity|\u221E)"
        );

        /**
         * Parses the {@link #positivePatterns} and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePatterns")
        public PsiInfinityExpression parsePositive(int lineNumber) {
            return create(Sign.POSITIVE, lineNumber);
        }

        /**
         * Parses the {@link #negativePatterns} and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePatterns")
        public PsiInfinityExpression parseNegative(int lineNumber) {
            return create(Sign.NEGATIVE, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param sign the sign of the infinity
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiInfinityExpression create(@NotNull Sign sign, int lineNumber) {
            return new PsiInfinityExpression(sign, lineNumber);
        }
    }

    /**
     * A sign to determine whether the infinity should be positive or negative
     *
     * @since 0.1.0
     */
    private enum Sign {
        POSITIVE,
        NEGATIVE
    }
}
