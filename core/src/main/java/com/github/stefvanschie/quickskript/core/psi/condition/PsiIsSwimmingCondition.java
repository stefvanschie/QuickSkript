package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Checks whether a living entity is currently swimming. This cannot be pre computed, since living entities may stop or
 * start swimming during game play.
 *
 * @since 0.1.0
 */
public class PsiIsSwimmingCondition extends PsiElement<Boolean> {

    /**
     * The living entity to check whether they're swimming
     */
    @NotNull
    protected final PsiElement<?> livingEntity;

    /**
     * False if the execution result needs to be inverted
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param livingEntity the living entity to check whether they're swimming
     * @param positive false if the execution result needs to be inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsSwimmingCondition(@NotNull PsiElement<?> livingEntity, boolean positive, int lineNumber) {
        super(lineNumber);

        this.livingEntity = livingEntity;
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
     * A factory for creating {@link PsiIsSwimmingCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiIsSwimmingCondition> {

        /**
         * A pattern for matching positive {@link PsiIsSwimmingCondition}s
         */
        @NotNull
        private final Pattern positivePattern = Pattern.compile("([\\s\\S]+) (?:is|are) swimming");

        /**
         * A pattern for matching negative {@link PsiIsSwimmingCondition}s
         */
        @NotNull
        private final Pattern negativePattern = Pattern.compile("([\\s\\S]+) (?:isn't|is not|aren't|are not) swimming");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Contract(pure = true)
        @Override
        public PsiIsSwimmingCondition tryParse(@NotNull String text, int lineNumber) {
            Matcher positiveMatcher = positivePattern.matcher(text);

            if (positiveMatcher.matches()) {
                PsiElement<?> livingEntity = SkriptLoader.get().forceParseElement(positiveMatcher.group(1), lineNumber);

                return create(livingEntity, true, lineNumber);
            }

            Matcher negativeMatcher = negativePattern.matcher(text);

            if (negativeMatcher.matches()) {
                PsiElement<?> livingEntity = SkriptLoader.get().forceParseElement(negativeMatcher.group(1), lineNumber);

                return create(livingEntity, false, lineNumber);
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param livingEntity the living entity to check whether they are swimming
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiIsSwimmingCondition create(@NotNull PsiElement<?> livingEntity, boolean positive, int lineNumber) {
            return new PsiIsSwimmingCondition(livingEntity, positive, lineNumber);
        }
    }
}
