package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Returns random numbers within a specified range.
 * The min parameter might return values greater than the ones returned by max, this has to be handled.
 * Both ends of the specified range are inclusive.
 * This element should never be pre computed (since then it will return the same number every time).
 *
 * @since 0.1.0
 */
public class PsiRandomNumberExpression extends PsiElement<Number> {

    /**
     * Stores whether an integer or a floating-point value should be returned
     */
    private final boolean integer;

    /**
     * The min and the max values. They may need to be switched to make their names truthful
     */
    @NotNull
    private final PsiElement<?> min, max;

    /**
     * Creates a new random number expression
     *
     * @param integer whether integers or floating point numbers should be generated
     * @param min one of the bounds of the valid value range
     * @param max the other bound of the valid value range
     * @param lineNumber the line number
     * @since 0.1.0
     */
    private PsiRandomNumberExpression(boolean integer, @NotNull PsiElement<?> min, @NotNull PsiElement<?> max,
                                      int lineNumber) {
        super(lineNumber);

        this.integer = integer;
        this.min = min;
        this.max = max;
    }

    @NotNull
    @Override
    protected Number executeImpl(@Nullable Context context) {
        Number minNumber = min.execute(context, Number.class);
        Number maxNumber = max.execute(context, Number.class);

        if (minNumber.doubleValue() > maxNumber.doubleValue()) {
            Number temp = maxNumber;
            maxNumber = minNumber;
            minNumber = temp;
        }

        ThreadLocalRandom random = ThreadLocalRandom.current();

        return integer ? random.nextLong(minNumber.longValue(), maxNumber.longValue() + 1)
                : random.nextDouble(minNumber.doubleValue(), maxNumber.doubleValue() + Double.MIN_VALUE);
    }

    /**
     * A factory for creating random number expressions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching random number expressions with integer values
         */
        @NotNull
        private final SkriptPattern integerPattern =
            SkriptPattern.parse("[a] random integer (from|between) %number% (to|and) %number%");

        /**
         * The pattern for matching random number expressions with floating point values
         */
        @NotNull
        private final SkriptPattern numberPattern =
            SkriptPattern.parse("[a] random number (from|between) %number% (to|and) %number%");

        /**
         * Parses the {@link #integerPattern} and invokes this method with its types if the match succeeds
         *
         * @param min the minimum value of the random number
         * @param max the maximum value of the random number
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("integerPattern")
        public PsiRandomNumberExpression parseInteger(@NotNull PsiElement<?> min, @NotNull PsiElement<?> max,
                                                      int lineNumber) {
            return create(true, min, max, lineNumber);
        }

        /**
         * Parses the {@link #numberPattern} and invokes this method with its types if the match succeeds
         *
         * @param min the minimum value of the random number
         * @param max the maximum value of the random number
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("numberPattern")
        public PsiRandomNumberExpression parseNumber(@NotNull PsiElement<?> min, @NotNull PsiElement<?> max,
                                                      int lineNumber) {
            return create(false, min, max, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param integer whether the results should be integers or floating points
         * @param min the minimum value
         * @param max the maximum value
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        protected PsiRandomNumberExpression create(boolean integer, @NotNull PsiElement<?> min,
                                                   @NotNull PsiElement<?> max, int lineNumber) {
            return new PsiRandomNumberExpression(integer, min, max, lineNumber);
        }
    }
}
