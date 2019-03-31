package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    protected boolean positive;

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
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Nullable
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        throw new UnsupportedOperationException("Cannot execute expression without implementation.");
    }

    /**
     * A factory for creating {@link PsiEventCancelledCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiEventCancelledCondition> {

        /**
         * The pattern used for matching {@link PsiEventCancelledCondition}s
         */
        @NotNull
        private Pattern pattern = Pattern.compile("(?:the )?event is( not|n't)? cancell?ed");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Contract(pure = true)
        @Override
        public PsiEventCancelledCondition tryParse(@NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches()) {
                return null;
            }

            return create(matcher.groupCount() >= 1, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
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
