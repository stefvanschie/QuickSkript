package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.Type;
import com.github.stefvanschie.quickskript.core.util.literal.TimeSpan;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether they provided time spans are infinite.
 *
 * @since 0.1.0
 */
public class PsiIsInfiniteCondition extends PsiElement<Boolean> {

    /**
     * The time spans to check if they are infinite.
     */
    private PsiElement<?> timeSpans;

    /**
     * If false, the result is negated.
     */
    private final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param timeSpans the time spans to check if they are infinite
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsInfiniteCondition(@NotNull PsiElement<?> timeSpans, boolean positive, int lineNumber) {
        super(lineNumber);

        this.timeSpans = timeSpans;
        this.positive = positive;

        if (this.timeSpans.isPreComputed()) {
            preComputed = executeImpl(null, null);

            this.timeSpans = null;
        }
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return this.positive == this.timeSpans.executeMulti(environment, context, TimeSpan.class)
            .test(TimeSpan::isInfinite);
    }

    /**
     * A factory for creating {@link PsiIsInfiniteCondition}s.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param timeSpans the time spans to check if they are infinite
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%time spans% (is|are) infinite")
        public PsiIsInfiniteCondition parsePositive(@NotNull PsiElement<?> timeSpans, int lineNumber) {
            return create(timeSpans, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param timeSpans the time spans to check if they are infinite
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%time spans% (isn't|is not|aren't|are not) infinite")
        public PsiIsInfiniteCondition parseNegative(@NotNull PsiElement<?> timeSpans, int lineNumber) {
            return create(timeSpans, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param timeSpans the time spans to check if they are infinite
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(value = "_, _, _, -> new", pure = true)
        public PsiIsInfiniteCondition create(@NotNull PsiElement<?> timeSpans, boolean positive, int lineNumber) {
            return new PsiIsInfiniteCondition(timeSpans, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
