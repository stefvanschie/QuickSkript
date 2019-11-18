package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
     * @param positive if false, the condition needs to be inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsPoisonedCondition(@NotNull PsiElement<?> livingEntity, boolean positive, int lineNumber) {
        super(lineNumber);

        this.livingEntity = livingEntity;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiIsPoisonedCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching positive {@link PsiIsPoisonedCondition}s
         */
        @NotNull
        private final SkriptPattern positivePattern = SkriptPattern.parse("%living entities% (is|are) poisoned");

        /**
         * The pattern for matching negative {@link PsiIsPoisonedCondition}s
         */
        @NotNull
        private final SkriptPattern negativePattern =
            SkriptPattern.parse("%living entities% (isn't|is not|aren't|are not) poisoned");

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param livingEntity the living entity to check the poisoned state for
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        public PsiIsPoisonedCondition parsePositive(@NotNull PsiElement<?> livingEntity, int lineNumber) {
            return create(livingEntity, true, lineNumber);
        }

        /**
         * Parses the {@link #negativePattern} and invokes this method with its types if the match succeeds
         *
         * @param livingEntity the living entity to check the poisoned state for
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        public PsiIsPoisonedCondition parseNegative(@NotNull PsiElement<?> livingEntity, int lineNumber) {
            return create(livingEntity, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param livingEntity the living entity to check the poisoned state for
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
