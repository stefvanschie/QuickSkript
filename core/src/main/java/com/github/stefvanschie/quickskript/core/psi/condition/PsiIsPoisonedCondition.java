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
 * Checks to see if a living entity is poisoned. This cannot be pre computed, since entities may get poisoned or be
 * healed of poison during game play.
 *
 * @since 0.1.0
 */
public class PsiIsPoisonedCondition extends PsiElement<Boolean> {

    /**
     * The living entity to check whether they are poisoned
     */
    @NotNull
    protected final PsiElement<?> livingEntity;

    /**
     * If false, the condition needs to be inverted
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param livingEntity the living entity to check whether they are poisoned
     * @param positive if false, the condition needs to be ivnerted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsPoisonedCondition(@NotNull PsiElement<?> livingEntity, boolean positive, int lineNumber) {
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
     * A factory for creating {@link PsiIsPoisonedCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiIsPoisonedCondition> {

        /**
         * The pattern for matching positive {@link PsiIsPoisonedCondition}s
         */
        @NotNull
        private final Pattern positivePattern = Pattern.compile("(?<livingEntity>[\\s\\S]+) (?:is|are) poisoned");

        /**
         * The pattern for matching negative {@link PsiIsPoisonedCondition}s
         */
        @NotNull
        private final Pattern negativePattern =
            Pattern.compile("(?<livingEntity>[\\s\\S]+) (?:isn't|is not|aren't|are not) poisoned");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Contract(pure = true)
        @Override
        public PsiIsPoisonedCondition tryParse(@NotNull String text, int lineNumber) {
            var skriptLoader = SkriptLoader.get();

            Matcher positiveMatcher = positivePattern.matcher(text);

            if (positiveMatcher.matches()) {
                String livingEntityGroup = positiveMatcher.group("livingEntity");

                PsiElement<?> livingEntity = skriptLoader.forceParseElement(livingEntityGroup, lineNumber);

                return create(livingEntity, true, lineNumber);
            }

            Matcher negativeMatcher = negativePattern.matcher(text);

            if (negativeMatcher.matches()) {
                String livingEntityGroup = negativeMatcher.group("livingEntity");

                PsiElement<?> livingEntity = skriptLoader.forceParseElement(livingEntityGroup, lineNumber);

                return create(livingEntity, false, lineNumber);
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param livingEntity the living entity to check the poisioned state for
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiIsPoisonedCondition create(@NotNull PsiElement<?> livingEntity, boolean positive, int lineNumber) {
            return new PsiIsPoisonedCondition(livingEntity, positive, lineNumber);
        }
    }
}
