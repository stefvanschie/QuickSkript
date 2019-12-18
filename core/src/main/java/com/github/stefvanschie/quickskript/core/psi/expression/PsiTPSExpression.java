package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the TPS over a certain period of time
 *
 * @since 0.1.0
 */
public class PsiTPSExpression extends PsiElement<double[]> {

    /**
     * The timespan for which to get the tps. If null, then all possible timespans should be returned.
     */
    @Nullable
    protected Timespan timespan;

    /**
     * Creates a new element with the given line number
     *
     * @param timespan the timespan for which to get the tps
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiTPSExpression(@Nullable Timespan timespan, int lineNumber) {
        super(lineNumber);

        this.timespan = timespan;
    }

    /**
     * A factory for creating {@link PsiTPSExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiTPSExpression}s without timespans
         */
        @NotNull
        private SkriptPattern pattern = SkriptPattern.parse("[the] tps");

        /**
         * The pattern for matching {@link PsiTPSExpression}s with a one minute timespan
         */
        @NotNull
        private SkriptPattern oneMinutePattern = SkriptPattern.parse("tps from [the] last ([1] minute|1[ ]m[inute])");

        /**
         * The pattern for matching {@link PsiTPSExpression}s with a five minute timespan
         */
        @NotNull
        private SkriptPattern fiveMinutePattern = SkriptPattern.parse("tps from [the] last 5[ ]m[inutes]");

        /**
         * The pattern for matching {@link PsiTPSExpression}s with a fifteen minute timespan
         */
        @NotNull
        private SkriptPattern fifteenMinutePattern = SkriptPattern.parse("tps from [the] last 15[ ]m[inutes]");

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiTPSExpression parse(int lineNumber) {
            return create(null, lineNumber);
        }

        /**
         * Parses the {@link #oneMinutePattern} and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("oneMinutePattern")
        public PsiTPSExpression parseOneMinute(int lineNumber) {
            return create(Timespan.ONE_MINUTE, lineNumber);
        }

        /**
         * Parses the {@link #fiveMinutePattern} and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("fiveMinutePattern")
        public PsiTPSExpression parseFiveMinute(int lineNumber) {
            return create(Timespan.FIVE_MINUTES, lineNumber);
        }

        /**
         * Parses the {@link #fifteenMinutePattern} and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("fifteenMinutePattern")
        public PsiTPSExpression parseFifteenMinute(int lineNumber) {
            return create(Timespan.FIFTEEN_MINUTES, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param timespan the timespan for which to get the tps
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiTPSExpression create(@Nullable Timespan timespan, int lineNumber) {
            return new PsiTPSExpression(timespan, lineNumber);
        }
    }

    /**
     * The timespan of the tps measurement
     *
     * @since 0.1.0
     */
    protected enum Timespan {

        /**
         * A one minute timespan
         *
         * @since 0.1.0
         */
        ONE_MINUTE,

        /**
         * A five minute timespan
         *
         * @since 0.1.0
         */
        FIVE_MINUTES,

        /**
         * A fifteen minute timespan
         *
         * @since 0.1.0
         */
        FIFTEEN_MINUTES
    }
}
