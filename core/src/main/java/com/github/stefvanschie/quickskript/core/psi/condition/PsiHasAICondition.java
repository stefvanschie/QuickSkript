package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if a living entity has artificial intelligence.
 *
 * @since 0.1.0
 */
public class PsiHasAICondition extends PsiElement<Boolean> {

    /**
     * The living entities to check if they have artificial intelligence.
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
     * @param livingEntities the living entities to check if they have artificial intelligence
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiHasAICondition(@NotNull PsiElement<?> livingEntities, boolean positive, int lineNumber) {
        super(lineNumber);

        this.livingEntities = livingEntities;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiHasAICondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param livingEntities the living entities to check if they have artificial intelligence
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%living entities% (has|have) (ai|artificial intelligence)")
        public PsiHasAICondition parsePositive(@NotNull PsiElement<?> livingEntities, int lineNumber) {
            return create(livingEntities, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param livingEntities the living entities to check if they don't have artificial intelligence
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%living entities% (doesn't|does not|do not|don't) have (ai|artificial intelligence)")
        public PsiHasAICondition parseNegative(@NotNull PsiElement<?> livingEntities, int lineNumber) {
            return create(livingEntities, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param livingEntities the living entities to check if they have artificial intelligence
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiHasAICondition create(@NotNull PsiElement<?> livingEntities, boolean positive, int lineNumber) {
            return new PsiHasAICondition(livingEntities, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
