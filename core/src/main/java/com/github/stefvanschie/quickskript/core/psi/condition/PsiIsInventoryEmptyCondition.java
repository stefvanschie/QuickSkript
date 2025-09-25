package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Check if the inventories are empty.
 *
 * @since 0.1.0
 */
public class PsiIsInventoryEmptyCondition extends PsiElement<Boolean> {

    /**
     * The inventories to check if they are empty.
     */
    @NotNull
    protected final PsiElement<?> inventories;

    /**
     * If false, the result is negated.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param inventories the inventories to check if they are empty
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsInventoryEmptyCondition(@NotNull PsiElement<?> inventories, boolean positive, int lineNumber) {
        super(lineNumber);

        this.inventories = inventories;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiIsInventoryEmptyCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param inventories the inventories to check if they are empty
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%inventories% (is|are) empty")
        public PsiIsInventoryEmptyCondition parsePositive(@NotNull PsiElement<?> inventories, int lineNumber) {
            return create(inventories, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param inventories the inventories to check if they aren't empty
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%inventories% (isn't|is not|aren't|are not) empty")
        public PsiIsInventoryEmptyCondition parseNegative(@NotNull PsiElement<?> inventories, int lineNumber) {
            return create(inventories, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param inventories the inventories to check if they are empty
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiIsInventoryEmptyCondition create(
            @NotNull PsiElement<?> inventories,
            boolean positive,
            int lineNumber
        ) {
            return new PsiIsInventoryEmptyCondition(inventories, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
