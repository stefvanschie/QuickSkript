package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if a number is divisible by another number, with an optional epsilon.
 *
 * @since 0.1.0
 */
public class PsiIsEvenlyDivisibleByCondition extends PsiElement<Boolean> {

    /**
     * The numbers to be divided.
     */
    private PsiElement<?> dividends;

    /**
     * The divisor.
     */
    private PsiElement<?> divisor;

    /**
     * The epsilon.
     */
    private PsiElement<?> epsilon;

    /**
     * If false, the result is negated.
     */
    private final boolean positive;

    /**
     * The default epsilon value used if no epsilon value is specified.
     */
    private static final double DEFAULT_EPSILON = 0.0000000001;

    /**
     * Creates a new element with the given line number
     *
     * @param dividends the numbers to be divided
     * @param divisor the divisor
     * @param epsilon the epsilon
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsEvenlyDivisibleByCondition(
        @NotNull PsiElement<?> dividends,
        @NotNull PsiElement<?> divisor,
        @Nullable PsiElement<?> epsilon,
        boolean positive,
        int lineNumber
    ) {
        super(lineNumber);

        this.dividends = dividends;
        this.divisor = divisor;
        this.epsilon = epsilon;
        this.positive = positive;

        if (this.epsilon == null) {
            this.epsilon = new PsiPrecomputedHolder<>(DEFAULT_EPSILON, lineNumber);
        }

        if (this.dividends.isPreComputed() && this.divisor.isPreComputed() && this.epsilon.isPreComputed()) {
            preComputed = executeImpl(null, null);

            this.dividends = null;
            this.divisor = null;
            this.epsilon = null;
        }
    }


    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        double divisor = this.divisor.execute(environment, context, Number.class).doubleValue();

        if (divisor == 0) {
            return !this.positive;
        }

        double epsilon = this.epsilon.execute(environment, context, Number.class).doubleValue();

        return this.positive == this.dividends.executeMulti(environment, context, Number.class)
            .map(number -> Math.abs(number.doubleValue() % divisor))
            .test(number -> number <= epsilon || number >= divisor - epsilon);
    }

    /**
     * A factory for constructing {@link PsiIsEvenlyDivisibleByCondition}s.
     *
     * @since 0.1.0
     */
    public static final class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param dividends the numbers to be divided
         * @param divisor the divisor
         * @param epsilon the epsilon
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%numbers% (is|are) evenly divisible by %number% [with [a] tolerance [of] %number%]")
        @Pattern("%numbers% can be evenly divided by %number% [with [a] tolerance [of] %number%]")
        public PsiIsEvenlyDivisibleByCondition parsePositive(
            @NotNull PsiElement<?> dividends,
            @NotNull PsiElement<?> divisor,
            @Nullable PsiElement<?> epsilon,
            int lineNumber
        ) {
            return create(dividends, divisor, epsilon, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param dividends the numbers to be divided
         * @param divisor the divisor
         * @param epsilon the epsilon
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%numbers% isn't evenly divisible by %number% [with [a] tolerance [of] %number%]")
        @Pattern("%numbers% is not evenly divisible by %number% [with [a] tolerance [of] %number%]")
        @Pattern("%numbers% aren't evenly divisible by %number% [with [a] tolerance [of] %number%]")
        @Pattern("%numbers% are not evenly divisible by %number% [with [a] tolerance [of] %number%]")
        @Pattern("%numbers% (can't|can[ ]not) be evenly divided by %number% [with [a] tolerance [of] %number%]")
        public PsiIsEvenlyDivisibleByCondition parseNegative(
            @NotNull PsiElement<?> dividends,
            @NotNull PsiElement<?> divisor,
            @Nullable PsiElement<?> epsilon,
            int lineNumber
        ) {
            return create(dividends, divisor, epsilon, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param dividends the numbers to be divided
         * @param divisor the divisor
         * @param epsilon the epsilon
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiIsEvenlyDivisibleByCondition create(
            @NotNull PsiElement<?> dividends,
            @NotNull PsiElement<?> divisor,
            @Nullable PsiElement<?> epsilon,
            boolean positive,
            int lineNumber
        ) {
            return new PsiIsEvenlyDivisibleByCondition(dividends, divisor, epsilon, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
