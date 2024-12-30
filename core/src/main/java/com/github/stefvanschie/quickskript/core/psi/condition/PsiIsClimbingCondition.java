package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if the living entities are climbing.
 *
 * @since 0.1.0
 */
public class PsiIsClimbingCondition extends PsiElement<Boolean> {

    /**
     * The living entities to check if they are climbing.
     */
    @NotNull
    protected final PsiElement<?> livingEntities;

    /**
     * If false, the result is negated.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsClimbingCondition(@NotNull PsiElement<?> livingEntities, boolean positive, int lineNumber) {
        super(lineNumber);

        this.livingEntities = livingEntities;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiIsClimbingCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching positive {@link PsiIsClimbingCondition}s
         */
        @NotNull
        private final SkriptPattern positivePattern = SkriptPattern.parse("%living entities% (is|are) climbing");

        /**
         * The pattern for matching negative {@link PsiIsClimbingCondition}s
         */
        @NotNull
        private final SkriptPattern negativePattern = SkriptPattern.parse(
            "%living entities% (isn't|is not|aren't|are not) climbing"
        );

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param livingEntities the living entities to check if they are climbing
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        public PsiIsClimbingCondition parsePositive(@NotNull PsiElement<?> livingEntities, int lineNumber) {
            return create(livingEntities, true, lineNumber);
        }

        /**
         * Parses the {@link #negativePattern} and invokes this method with its types if the match succeeds
         *
         * @param livingEntities the living entities to check if they aren't climbing
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        public PsiIsClimbingCondition parseNegative(@NotNull PsiElement<?> livingEntities, int lineNumber) {
            return create(livingEntities, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param livingEntities the living entities to check if they are climbing
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiIsClimbingCondition create(@NotNull PsiElement<?> livingEntities, boolean positive, int lineNumber) {
            return new PsiIsClimbingCondition(livingEntities, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
