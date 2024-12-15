package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if the blocks are indirectly powered by redstone.
 *
 * @since 0.1.0
 */
public class PsiIsBlockIndirectlyRedstonePoweredCondition extends PsiElement<Boolean> {

    /**
     * The blocks to check if they are indirectly powered by redstone.
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
     * @param blocks the blocks to check if they are indirectly powered by redstone
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsBlockIndirectlyRedstonePoweredCondition(@NotNull PsiElement<?> blocks, boolean positive, int lineNumber) {
        super(lineNumber);

        this.blocks = blocks;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiIsBlockIndirectlyRedstonePoweredCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching positive {@link PsiIsBlockIndirectlyRedstonePoweredCondition}s
         */
        @NotNull
        private final SkriptPattern positivePattern = SkriptPattern.parse(
            "%blocks% (is|are) indirectly redstone powered"
        );

        /**
         * The pattern for matching negative {@link PsiIsBlockIndirectlyRedstonePoweredCondition}s
         */
        @NotNull
        private final SkriptPattern negativePattern = SkriptPattern.parse(
            "%blocks% (is|are)(n't| not) indirectly redstone powered"
        );

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param blocks the blocks to check if they are indrectly powered by redstone
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        public PsiIsBlockIndirectlyRedstonePoweredCondition parsePositive(
            @NotNull PsiElement<?> blocks,
            int lineNumber
        ) {
            return create(blocks, true, lineNumber);
        }

        /**
         * Parses the {@link #negativePattern} and invokes this method with its types if the match succeeds
         *
         * @param blocks the blocks to check if they aren't indrectly powered by redstone
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        public PsiIsBlockIndirectlyRedstonePoweredCondition parseNegative(
            @NotNull PsiElement<?> blocks,
            int lineNumber
        ) {
            return create(blocks, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param blocks the blocks to check if they are indirectly powered by redstone
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiIsBlockIndirectlyRedstonePoweredCondition create(
            @NotNull PsiElement<?> blocks,
            boolean positive,
            int lineNumber
        ) {
            return new PsiIsBlockIndirectlyRedstonePoweredCondition(blocks, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
