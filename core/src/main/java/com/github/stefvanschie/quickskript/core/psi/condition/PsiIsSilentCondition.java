package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if the entities are silent.
 *
 * @since 0.1.0
 */
public class PsiIsSilentCondition extends PsiElement<Boolean> {

    /**
     * The entities to check if they are silent.
     */
    @NotNull
    protected PsiElement<?> entities;

    /**
     * If false, the result is negated.
     */
    protected boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param entities the entities to check if they are silent
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsSilentCondition(@NotNull PsiElement<?> entities, boolean positive, int lineNumber) {
        super(lineNumber);

        this.entities = entities;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiIsSilentCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param entities the entities to check if they are silent
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%entities% (is|are) silent")
        public PsiIsSilentCondition parsePositive(@NotNull PsiElement<?> entities, int lineNumber) {
            return create(entities, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param entities the entities to check if they are silent
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%entities% (isn't|is not|aren't|are not) silent")
        public PsiIsSilentCondition parseNegative(@NotNull PsiElement<?> entities, int lineNumber) {
            return create(entities, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param entities the entities to check if they are silent
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiIsSilentCondition create(@NotNull PsiElement<?> entities, boolean positive, int lineNumber) {
            return new PsiIsSilentCondition(entities, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public String getType() {
            return "boolean";
        }
    }
}