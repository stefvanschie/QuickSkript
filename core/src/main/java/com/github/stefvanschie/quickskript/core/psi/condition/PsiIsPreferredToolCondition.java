package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks whether the given item types are the preferred tools for the provided blocks or block data.
 *
 * @since 0.1.0
 */
public class PsiIsPreferredToolCondition extends PsiElement<Boolean> {

    /**
     * The item types to check if they are the preferred tools.
     */
    @NotNull
    protected final PsiElement<?> itemTypes;

    /**
     * The blocks or block data to check the preferred tool for.
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
     * @param itemTypes the item types to check if they are the preferred tools
     * @param blocks the blocks or block data to check the preferred tool for
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsPreferredToolCondition(@NotNull PsiElement<?> itemTypes, @NotNull PsiElement<?> blocks,
                                          boolean positive, int lineNumber) {
        super(lineNumber);

        this.itemTypes = itemTypes;
        this.blocks = blocks;
        this.positive = positive;
    }

    /**
     * A factory to create instances of {@link PsiIsPreferredToolCondition}.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param itemTypes the item types to check if they are the preferred tools
         * @param blocks the blocks or block data to check the preferred tool for
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%item types% (is|are) %blocks/block datas%'s preferred tool[s]")
        @Pattern("%item types% (is|are) [the|a] preferred tool[s] (for|of) %blocks/block datas%")
        public PsiIsPreferredToolCondition parsePositive(@NotNull PsiElement<?> itemTypes,
                                                         @NotNull PsiElement<?> blocks, int lineNumber) {
            return create(itemTypes, blocks, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param itemTypes the item types to check if they are the preferred tools
         * @param blocks the blocks or block data to check the preferred tool for
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%item types% (is|are)(n't| not) %blocks/block datas%'s preferred tool[s]")
        @Pattern("%item types% (is|are)(n't| not) [the|a] preferred tool[s] (for|of) %blocks/block datas%")
        public PsiIsPreferredToolCondition parseNegative(@NotNull PsiElement<?> itemTypes,
                                                         @NotNull PsiElement<?> blocks, int lineNumber) {
            return create(itemTypes, blocks, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param itemTypes the item types to check if they are the preferred tools
         * @param blocks the blocks or block data to check the preferred tool for
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(value = "_, _, _, _ -> new", pure = true)
        public PsiIsPreferredToolCondition create(@NotNull PsiElement<?> itemTypes, @NotNull PsiElement<?> blocks,
                                                  boolean positive, int lineNumber) {
            return new PsiIsPreferredToolCondition(itemTypes, blocks, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public String getType() {
            return "boolean";
        }
    }
}
