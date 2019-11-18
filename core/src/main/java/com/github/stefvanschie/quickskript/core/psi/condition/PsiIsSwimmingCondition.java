package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
     * A factory for creating {@link PsiIsSwimmingCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * A pattern for matching positive {@link PsiIsSwimmingCondition}s
         */
        @NotNull
        private final SkriptPattern positivePattern = SkriptPattern.parse("%living entities% (is|are) swimming");

        /**
         * A pattern for matching negative {@link PsiIsSwimmingCondition}s
         */
        @NotNull
        private final SkriptPattern negativePattern =
            SkriptPattern.parse("%living entities% (isn't|is not|aren't|are not) swimming");

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param livingEntity the living entity to check whether they are swimming
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        public PsiIsSwimmingCondition parsePositive(@NotNull PsiElement<?> livingEntity, int lineNumber) {
            return create(livingEntity, true, lineNumber);
        }

        /**
         * Parses the {@link #negativePattern} and invokes this method with its types if the match succeeds
         *
         * @param livingEntity the living entity to check whether they are swimming
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        public PsiIsSwimmingCondition parseNegative(@NotNull PsiElement<?> livingEntity, int lineNumber) {
            return create(livingEntity, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
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
