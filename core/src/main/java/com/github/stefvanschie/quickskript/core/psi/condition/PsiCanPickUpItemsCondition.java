package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if the specified living entities can pick up items.
 *
 * @since 0.1.0
 */
public class PsiCanPickUpItemsCondition extends PsiElement<Boolean> {

    /**
     * The living entities to check whether they can pick up items.
     */
    @NotNull
    protected final PsiElement<?> livingEntities;

    /**
     * If false, the execution result should be negated.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiCanPickUpItemsCondition(@NotNull PsiElement<?> livingEntities, boolean positive, int lineNumber) {
        super(lineNumber);

        this.livingEntities = livingEntities;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiCanPickUpItemsCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param livingEntities the living entities to check
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%living entities% can pick([ ]up items| items up)")
        public PsiCanPickUpItemsCondition parsePositive(@NotNull PsiElement<?> livingEntities, int lineNumber) {
            return create(livingEntities, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param livingEntities the living entities to check
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%living entities% (can't|cannot|can not) pick([ ]up items| items up)")
        public PsiCanPickUpItemsCondition parseNegative(@NotNull PsiElement<?> livingEntities, int lineNumber) {
            return create(livingEntities, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiCanPickUpItemsCondition create(
            @NotNull PsiElement<?> livingEntities,
            boolean positive,
            int lineNumber
        ) {
            return new PsiCanPickUpItemsCondition(livingEntities, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
