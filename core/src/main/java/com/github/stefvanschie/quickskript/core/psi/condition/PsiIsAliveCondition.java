package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks whether a living entity is alive or not. This cannot be pre computed, since the living entity may be
 * killed/revived during game play.
 *
 * @since 0.1.0
 */
public class PsiIsAliveCondition extends PsiElement<Boolean> {

    /**
     * The living entity to check the alive state for
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
     * @param livingEntity the living entity to check the alive state for, see {@link #livingEntity}
     * @param positive false if the result of execution needs to be inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsAliveCondition(@NotNull PsiElement<?> livingEntity, boolean positive, int lineNumber) {
        super(lineNumber);

        this.livingEntity = livingEntity;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiIsAliveCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching positive is alive conditions
         */
        @NotNull
        private final SkriptPattern positivePattern = SkriptPattern.parse("%entities% (is|are) (alive|dead)");

        /**
         * The pattern for matching negative is alive conditions
         */
        @NotNull
        private final SkriptPattern negativePattern =
            SkriptPattern.parse("%entities% (isn't|is not|aren't|are not) (alive|dead)");

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param livingEntity the entity to check the alive state for
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        public PsiIsAliveCondition parsePositive(@NotNull PsiElement<?> livingEntity, int lineNumber) {
            return create(livingEntity, true, lineNumber);
        }

        /**
         * Parses the {@link #negativePattern} and invokes this method with its types if the match succeeds
         *
         * @param livingEntity the entity to check the alive state for
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        public PsiIsAliveCondition parseNegative(@NotNull PsiElement<?> livingEntity, int lineNumber) {
            return create(livingEntity, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param livingEntity the living entity to check the alive state for
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiIsAliveCondition create(@NotNull PsiElement<?> livingEntity, boolean positive, int lineNumber) {
            return new PsiIsAliveCondition(livingEntity, positive, lineNumber);
        }
    }
}
