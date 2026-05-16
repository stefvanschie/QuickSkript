package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if the blocks are passable.
 *
 * @since 0.1.0
 */
public class PsiIsPassableCondition extends PsiElement<Boolean> {

    /**
     * The blocks to check if they are passable.
     */
    @NotNull
    protected final PsiElement<?> blocks;

    /**
     * If false, the result is negated.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param blocks the blocks to check if they are passable
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsPassableCondition(@NotNull PsiElement<?> blocks, boolean positive, int lineNumber) {
        super(lineNumber);

        this.blocks = blocks;
        this.positive = positive;
    }

    /**
     * A factory to create instances of {@link PsiIsPassableCondition}.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param blocks the blocks to check if they are passable
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%blocks% (is|are) passable")
        public PsiIsPassableCondition parsePositive(@NotNull PsiElement<?> blocks, int lineNumber) {
            return create(blocks, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param blocks the blocks to check if they are passable
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%blocks% (isn't|is not|aren't|are not) passable")
        public PsiIsPassableCondition parseNegative(@NotNull PsiElement<?> blocks, int lineNumber) {
            return create(blocks, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param blocks the blocks to check if they are passable
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        public PsiIsPassableCondition create(@NotNull PsiElement<?> blocks, boolean positive, int lineNumber) {
            return new PsiIsPassableCondition(blocks, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public String getType() {
            return "boolean";
        }
    }
}
