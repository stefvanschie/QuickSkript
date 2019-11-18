package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A condition to check whether or not the event has been cancelled. This cannot be pre computed, since it is dependent
 * on {@link Context}.
 *
 * @since 0.1.0
 */
public class PsiEventCancelledCondition extends PsiElement<Boolean> {

    /**
     * If false, the result of execution will be inverted.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param positive if false, the result of execution will be inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiEventCancelledCondition(boolean positive, int lineNumber) {
        super(lineNumber);

        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiEventCancelledCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern used for matching positive {@link PsiEventCancelledCondition}s
         */
        @NotNull
        private final SkriptPattern positivePattern = SkriptPattern.parse("[the] event is cancel[l]ed");

        /**
         * The pattern used for matching negative{@link PsiEventCancelledCondition}s
         */
        @NotNull
        private final SkriptPattern negativePattern = SkriptPattern.parse("[the] event (is not|isn't) cancel[l]ed");

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        public PsiEventCancelledCondition parsePositive(int lineNumber) {
            return create(true, lineNumber);
        }

        /**
         * Parses the {@link #negativePattern} and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        public PsiEventCancelledCondition parseNegative(int lineNumber) {
            return create(false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param positive false if the result should be negated
         * @param lineNumber the line number
         * @return the can see condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiEventCancelledCondition create(boolean positive, int lineNumber) {
            return new PsiEventCancelledCondition(positive, lineNumber);
        }
    }
}
