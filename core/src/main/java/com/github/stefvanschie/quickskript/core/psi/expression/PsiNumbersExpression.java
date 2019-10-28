package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Gets a list of numbers between specified bounds
 *
 * @since 0.1.0
 */
public class PsiNumbersExpression extends PsiElement<List<Number>> {

    /**
     * The lower and upper bound for the to be generated numbers. If the number of the lower bound is higher than the
     * number of the upper bound, the resulting list will be descending.
     */
    private PsiElement<?> lowerBound, upperBound;

    /**
     * If true, the resulting list will only contain whole numbers; if false, the resulting list may contain fractional
     * numbers
     */
    private boolean integer;

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiNumbersExpression(@NotNull PsiElement<?> lowerBound, @NotNull PsiElement<?> upperBound, boolean integer,
        int lineNumber) {
        super(lineNumber);

        this.lowerBound = lowerBound;
        this.upperBound = upperBound;

        this.integer = integer;

        if (lowerBound.isPreComputed() && upperBound.isPreComputed()) {
            preComputed = executeImpl(null);

            this.lowerBound = null;
            this.upperBound = null;
        }
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected List<Number> executeImpl(@Nullable Context context) {
        List<Number> numbers = new ArrayList<>();

        double lowerBound = this.lowerBound.execute(context, Double.class);
        double upperBound = this.upperBound.execute(context, Double.class);

        int incrementer = lowerBound < upperBound ? 1 : -1;

        if (integer) {
            if (incrementer == 1) {
                lowerBound = Math.ceil(lowerBound);
                upperBound = Math.floor(upperBound);
            } else {
                lowerBound = Math.floor(lowerBound);
                upperBound = Math.ceil(upperBound);
            }
        }

        double currentNumber = lowerBound;

        while (Math.min(lowerBound, upperBound) <= currentNumber && currentNumber <= Math.max(lowerBound, upperBound)) {
            numbers.add(currentNumber);

            currentNumber += incrementer;
        }

        return numbers;
    }

    /**
     * A factory for creating {@link PsiNumbersExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiNumbersExpression}s
         */
        @NotNull
        private SkriptPattern pattern = SkriptPattern.parse(
            "[(all [[of] the]|the)] (numbers|1\u00A6integers) (between|from) %number% (and|to) %number%"
        );

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param result the result of parsing the {@link #pattern}
         * @param lowerBound the lower bound of the number list
         * @param upperBound the upper bound of the number list
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiNumbersExpression parse(@NotNull SkriptMatchResult result, @NotNull PsiElement<?> lowerBound,
            @NotNull PsiElement<?> upperBound, int lineNumber) {
            return create(lowerBound, upperBound, result.getParseMark() == 1, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param lowerBound the lower bound of the number list
         * @param upperBound the upper bound of the number list
         * @param integer true if the numbers may not contain fractions; false otherwise
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiNumbersExpression create(@NotNull PsiElement<?> lowerBound, @NotNull PsiElement<?> upperBound,
            boolean integer, int lineNumber) {
            return new PsiNumbersExpression(lowerBound, upperBound, integer, lineNumber);
        }
    }
}
