package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Rounds a number in a specified manner. This may be pre-computed.
 *
 * @since 0.1.0
 */
public class PsiRoundExpression extends PsiElement<Integer> {

    /**
     * The number to round
     */
    private PsiElement<?> number;

    /**
     * How the number should be rounded
     */
    private RoundMode roundMode;

    /**
     * Creates a new element with the given line number
     *
     * @param number the number to round
     * @param roundMode how the number should be rounded
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiRoundExpression(@NotNull PsiElement<?> number, @NotNull RoundMode roundMode, int lineNumber) {
        super(lineNumber);

        this.number = number;
        this.roundMode = roundMode;

        if (number.isPreComputed()) {
            preComputed = executeImpl(null, null);

            this.number = null;
            this.roundMode = null;
        }
    }

    @NotNull
    @Override
    protected Integer executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        if (roundMode == RoundMode.DOWN) {
            return (int) Math.floor(number.execute(environment, context, Number.class).doubleValue());
        }

        if (roundMode == RoundMode.NEAREST) {
            return (int) Math.round(number.execute(environment, context, Number.class).doubleValue());
        }

        if (roundMode == RoundMode.UP) {
            return (int) Math.ceil(number.execute(environment, context, Number.class).doubleValue());
        }

        throw new ExecutionException(new UnsupportedOperationException("Unknown rounding mode"), lineNumber);
    }

    /**
     * A factory for creating {@link PsiRoundExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param number the number to round
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("[a|the] round[ed] down %number%")
        public PsiRoundExpression parseDown(@NotNull PsiElement<?> number, int lineNumber) {
            return create(number, RoundMode.DOWN, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param number the number to round
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("[a|the] round[ed] %number%")
        public PsiRoundExpression parseNearest(@NotNull PsiElement<?> number, int lineNumber) {
            return create(number, RoundMode.NEAREST, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param number the number to round
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("[a|the] round[ed] up %number%")
        public PsiRoundExpression parseUp(@NotNull PsiElement<?> number, int lineNumber) {
            return create(number, RoundMode.UP, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param number the number to round
         * @param roundMode how the number should be rounded
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiRoundExpression create(@NotNull PsiElement<?> number, @NotNull RoundMode roundMode, int lineNumber) {
            return new PsiRoundExpression(number, roundMode, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.NUMBER;
        }
    }

    /**
     * Specifies how a number should be rounded
     *
     * @since 0.1.0
     */
    private enum RoundMode {

        /**
         * Rounds the number towards negative infinity
         *
         * @since 0.1.0
         */
        DOWN,

        /**
         * Rounds the number to the nearest integer
         *
         * @since 0.1.0
         */
        NEAREST,

        /**
         * Rounds the number towards positive infinity
         *
         * @since 0.1.0
         */
        UP
    }
}
