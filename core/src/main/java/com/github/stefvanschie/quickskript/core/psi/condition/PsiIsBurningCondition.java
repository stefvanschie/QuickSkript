package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks whether an entity is burning. This cannot be pre computed, since an entity can get burned and/or extinguished
 * during game play.
 *
 * @since 0.1.0
 */
public class PsiIsBurningCondition extends PsiElement<Boolean> {

    /**
     * The entity to check whether they are burned
     */
    @NotNull
    protected final PsiElement<?> entity;

    /**
     * False if the execution result needs to be inverted
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param entity the entity to check whether they are burning
     * @param positive false if the execution result needs to be inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsBurningCondition(@NotNull PsiElement<?> entity, boolean positive, int lineNumber) {
        super(lineNumber);

        this.entity = entity;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiIsBurningCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching positive is burning conditions
         */
        @NotNull
        private final SkriptPattern positivePattern =
            SkriptPattern.parse("%entities% (is|are) (burning|ignited|on fire)");

        /**
         * The pattern for matching negative is burning conditions
         */
        @NotNull
        private final SkriptPattern negativePattern =
            SkriptPattern.parse("%entities% (isn't|is not|aren't|are not) (burning|ignited|on fire)");

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param entity the entity to check whether they are burning
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        public PsiIsBurningCondition parsePositive(@NotNull PsiElement<?> entity, int lineNumber) {
            return create(entity, true, lineNumber);
        }

        /**
         * Parses the {@link #negativePattern} and invokes this method with its types if the match succeeds
         *
         * @param entity the entity to check whether they are burning
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        public PsiIsBurningCondition parseNegative(@NotNull PsiElement<?> entity, int lineNumber) {
            return create(entity, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param entity the entity to check whether they are burning
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiIsBurningCondition create(@NotNull PsiElement<?> entity, boolean positive, int lineNumber) {
            return new PsiIsBurningCondition(entity, positive, lineNumber);
        }
    }
}
