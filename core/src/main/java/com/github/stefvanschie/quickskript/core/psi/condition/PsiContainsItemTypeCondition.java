package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks whether a given inventory contains a given item type.
 *
 * @since 0.1.0
 */
public class PsiContainsItemTypeCondition extends PsiElement<Boolean> {

    /**
     * The inventory to check.
     */
    @NotNull
    protected final PsiElement<?> inventory;

    /**
     * The item type to check if it is contained in the inventory.
     */
    @NotNull
    protected final PsiElement<?> itemType;

    /**
     * If false, the result is inverted.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number.
     *
     * @param inventory the inventory to check
     * @param itemType the item type to check if it is contained in the inventory
     * @param positive if false, the result is inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiContainsItemTypeCondition(
        @NotNull PsiElement<?> inventory,
        @NotNull PsiElement<?> itemType,
        boolean positive,
        int lineNumber
    ) {
        super(lineNumber);

        this.inventory = inventory;
        this.itemType = itemType;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiContainsItemTypeCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching positive {@link PsiContainsItemTypeCondition}s
         */
        @NotNull
        private final SkriptPattern[] positivePattern = new SkriptPattern[] {
            SkriptPattern.parse("%inventories% (has|have) %item types% [in [the[ir]|his|her|its] inventory]"),
            SkriptPattern.parse("%inventories% contain[s] %item types%")
        };

        /**
         * The pattern for matching negative {@link PsiContainsItemTypeCondition}s
         */
        @NotNull
        private final SkriptPattern[] negativePattern = new SkriptPattern[] {
            SkriptPattern.parse(
                "%inventories% (doesn't|does not|do not|don't) have %item types% [in [the[ir]|his|her|its] inventory]"
            ),
            SkriptPattern.parse("%inventories% (doesn't|does not|do not|don't) contain %item types%")
        };

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param inventory the inventory to check
         * @param itemType the item type to check if it is contained in the inventory
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        public PsiContainsItemTypeCondition parsePositive(
            @NotNull PsiElement<?> inventory,
            @NotNull PsiElement<?> itemType,
            int lineNumber
        ) {
            return create(inventory, itemType, true, lineNumber);
        }

        /**
         * Parses the {@link #negativePattern} and invokes this method with its types if the match succeeds
         *
         * @param inventory the inventory to check
         * @param itemType the item type to check if it is contained in the inventory
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        public PsiContainsItemTypeCondition parseNegative(
            @NotNull PsiElement<?> inventory,
            @NotNull PsiElement<?> itemType,
            int lineNumber
        ) {
            return create(inventory, itemType, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param inventory the inventory to check
         * @param itemType the item type to check if it is contained in the inventory
         * @param positive if false, the result is inverted
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiContainsItemTypeCondition create(
            @NotNull PsiElement<?> inventory,
            @NotNull PsiElement<?> itemType,
            boolean positive,
            int lineNumber
        ) {
            return new PsiContainsItemTypeCondition(inventory, itemType, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
