package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if the entities are persistent.
 *
 * @since 0.1.0
 */
public class PsiEntityIsPersistentCondition extends PsiElement<Boolean> {

    /**
     * The entities to check if they are persistent.
     */
    @NotNull
    protected final PsiElement<?> entities;

    /**
     * If false, the result is negated.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param entities the entities to check if they are persistent
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiEntityIsPersistentCondition(@NotNull PsiElement<?> entities, boolean positive, int lineNumber) {
        super(lineNumber);

        this.entities = entities;
        this.positive = positive;
    }

    /**
     * A factory for creating instances of {@link PsiEntityIsPersistentCondition}.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param entities the entities to check if they are persistent
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%entities% (is|are) persistent")
        public PsiEntityIsPersistentCondition parsePositive(@NotNull PsiElement<?> entities, int lineNumber) {
            return create(entities, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param entities the entities to check if they are persistent
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%entities% (isn't|is not|aren't|are not) persistent")
        public PsiEntityIsPersistentCondition parseNegative(@NotNull PsiElement<?> entities, int lineNumber) {
            return create(entities, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param entities the entities to check if they are persistent
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiEntityIsPersistentCondition create(@NotNull PsiElement<?> entities, boolean positive, int lineNumber) {
            return new PsiEntityIsPersistentCondition(entities, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public String getType() {
            return "boolean";
        }
    }
}
