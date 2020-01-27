package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.util.literal.InventoryAction;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets an inventory action
 *
 * @since 0.1.0
 */
public class PsiInventoryActionLiteral extends PsiPrecomputedHolder<InventoryAction> {

    /**
     * Creates a new element with the given line number
     *
     * @param inventoryAction the inventory action
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiInventoryActionLiteral(@NotNull InventoryAction inventoryAction, int lineNumber) {
        super(inventoryAction, lineNumber);
    }

    /**
     * A factory for creating {@link PsiInventoryActionLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * This gets called upon parsing
         *
         * @param text the text to parse
         * @param lineNumber the line number
         * @return the literal, or null to indicate failure
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiInventoryActionLiteral parse(@NotNull String text, int lineNumber) {
            for (InventoryAction inventoryAction : InventoryAction.values()) {
                if (inventoryAction.name().replace('_', ' ').equalsIgnoreCase(text)) {
                    return create(inventoryAction, lineNumber);
                }
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param inventoryAction the inventory action
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiInventoryActionLiteral create(@NotNull InventoryAction inventoryAction, int lineNumber) {
            return new PsiInventoryActionLiteral(inventoryAction, lineNumber);
        }
    }
}
