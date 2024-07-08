package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if the specified inventories can hold the specified item categories.
 *
 * @since 0.1.0
 */
public class PsiCanHoldCondition extends PsiElement<Boolean> {

    /**
     * The inventory to check whether the item categories fit in.
     */
    @NotNull
    protected final PsiElement<?> inventories;

    /**
     * The item types to check to see if it fits in the inventory.
     */
    @NotNull
    protected final PsiElement<?> itemTypes;

    /**
     * If false, the execution result should be inverted.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param inventories the inventories to check if it can contain the given item category
     * @param itemTypes the item types to check if it can fit in the inventory
     * @param positive if false, the execution result will be inverted.
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiCanHoldCondition(
        @NotNull PsiElement<?> inventories,
        @NotNull PsiElement<?> itemTypes,
        boolean positive,
        int lineNumber
    ) {
        super(lineNumber);

        this.inventories = inventories;
        this.itemTypes = itemTypes;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiCanHoldCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching positive {@link PsiCanHoldCondition}s
         */
        @NotNull
        private final SkriptPattern positivePattern = SkriptPattern.parse(
            "%inventories% (can hold|ha(s|ve) [enough] space (for|to hold)) %item types%"
        );

        /**
         * The pattern for matching negative {@link PsiCanHoldCondition}s
         */
        @NotNull
        private final SkriptPattern negativePattern = SkriptPattern.parse(
            "%inventories% (can(no|')t hold|(ha(s|ve) not|ha(s|ve)n't|do[es]n't have) [enough] space (for|to hold)) %item types%"
        );

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param inventories the inventory to check if it can contain the given item categories
         * @param itemTypes the item type to check if it can fit in the inventories
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        public PsiCanHoldCondition parsePositive(
            @NotNull PsiElement<?> inventories,
            @NotNull PsiElement<?> itemTypes,
            int lineNumber
        ) {
            return create(inventories, itemTypes, true, lineNumber);
        }

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param inventories the inventories to check if it can contain the given item categories
         * @param itemTypes the item types to check if it can fit in the inventories
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        public PsiCanHoldCondition parseNegative(
            @NotNull PsiElement<?> inventories,
            @NotNull PsiElement<?> itemTypes,
            int lineNumber
        ) {
            return create(inventories, itemTypes, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param inventories the inventories to check if it can contain the given item categories
         * @param itemTypes the item types to check if it can fit in the inventories
         * @param positive if false, the execution result will be inverted
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiCanHoldCondition create(
            @NotNull PsiElement<?> inventories,
            @NotNull PsiElement<?> itemTypes,
            boolean positive,
            int lineNumber
        ) {
            return new PsiCanHoldCondition(inventories, itemTypes, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
