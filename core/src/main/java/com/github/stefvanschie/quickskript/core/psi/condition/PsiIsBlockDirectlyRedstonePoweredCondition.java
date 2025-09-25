package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if blocks are directly powered by redstone.
 *
 * @since 0.1.0
 */
public class PsiIsBlockDirectlyRedstonePoweredCondition extends PsiElement<Boolean> {

    /**
     * The blocks to check if they are directly powered by redstone.
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
     * @param blocks the blocks to check if they are directly powered by redstone
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsBlockDirectlyRedstonePoweredCondition(
        @NotNull PsiElement<?> blocks,
        boolean positive,
        int lineNumber
    ) {
        super(lineNumber);

        this.blocks = blocks;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiIsBlockDirectlyRedstonePoweredCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param blocks the blocks to check if they are directly powered by redstone
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%blocks% (is|are) redstone powered")
        public PsiIsBlockDirectlyRedstonePoweredCondition parsePositive(@NotNull PsiElement<?> blocks, int lineNumber) {
            return create(blocks, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param blocks the blocks to check if they aren't directly powered by redstone
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%blocks% (is|are)(n't| not) redstone powered")
        public PsiIsBlockDirectlyRedstonePoweredCondition parseNegative(@NotNull PsiElement<?> blocks, int lineNumber) {
            return create(blocks, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param blocks the blocks to check if they aren't directly powered by redstone
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiIsBlockDirectlyRedstonePoweredCondition create(
            @NotNull PsiElement<?> blocks,
            boolean positive,
            int lineNumber
        ) {
            return new PsiIsBlockDirectlyRedstonePoweredCondition(blocks, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
