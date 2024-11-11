package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

/**
 * Checks if the given dates have passed.
 *
 * @since 0.1.0
 */
public class PsiHasPassed extends PsiElement<Boolean> {

    /**
     * The dates to check if they have passed.
     */
    private PsiElement<? extends LocalDateTime> dates;

    /**
     * If false, this checks if the given dates have not passed.
     */
    private final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param dates the dates to check if they have passed
     * @param positive if false, this checks if the given dates have not passed
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiHasPassed(@NotNull PsiElement<? extends LocalDateTime> dates, boolean positive, int lineNumber) {
        super(lineNumber);

        this.dates = dates;
        this.positive = positive;

        if (dates.isPreComputed()) {
            super.preComputed = executeImpl(null, null);

            this.dates = null;
        }
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        MultiResult<? extends LocalDateTime> dates = this.dates.executeMulti(environment, context);
        LocalDateTime now = LocalDateTime.now();

        if (this.positive) {
            return dates.test(date -> date.isBefore(now));
        } else {
            return dates.test(date -> date.isAfter(now));
        }
    }

    /**
     * A factory for creating {@link PsiHasPassed}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching positive {@link PsiHasPassed}s
         */
        @NotNull
        private final SkriptPattern[] positivePatterns = SkriptPattern.parse(
            "%dates% (is|are) in the past",
            "%dates% (is|are)(n't| not) in the future",
            "%dates% ha(s|ve) passed"
        );

        /**
         * The pattern for matching negative {@link PsiHasPassed}s
         */
        @NotNull
        private final SkriptPattern[] negativePatterns = SkriptPattern.parse(
            "%dates% (is|are) in the future",
            "%dates% (is|are)(n't| not) in the past",
            "%dates% ha(s|ve)(n't| not) passed"
        );

        /**
         * Parses the {@link #positivePatterns} and invokes this method with its types if the match succeeds
         *
         * @param dates the dates to check if they have passed
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePatterns")
        public PsiHasPassed parsePositive(@NotNull PsiElement<? extends LocalDateTime> dates, int lineNumber) {
            return create(dates, true, lineNumber);
        }

        /**
         * Parses the {@link #negativePatterns} and invokes this method with its types if the match succeeds
         *
         * @param dates the dates to check if they have not passed
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePatterns")
        public PsiHasPassed parseNegative(@NotNull PsiElement<? extends LocalDateTime> dates, int lineNumber) {
            return create(dates, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param dates the dates to check if they have passed
         * @param positive if false, this checks if the given dates have not passed
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiHasPassed create(
            @NotNull PsiElement<? extends LocalDateTime> dates,
            boolean positive,
            int lineNumber
        ) {
            return new PsiHasPassed(dates, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
