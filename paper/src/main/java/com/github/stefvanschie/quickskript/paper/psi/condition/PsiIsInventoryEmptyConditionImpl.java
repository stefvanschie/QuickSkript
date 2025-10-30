package com.github.stefvanschie.quickskript.paper.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsInventoryEmptyCondition;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the inventories are empty.
 *
 * @since 0.1.0
 */
public class PsiIsInventoryEmptyConditionImpl extends PsiIsInventoryEmptyCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param inventories the inventories to check if they are empty
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsInventoryEmptyConditionImpl(@NotNull PsiElement<?> inventories, boolean positive, int lineNumber) {
        super(inventories, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        MultiResult<? extends Inventory> inventories = super.inventories.executeMulti(
            environment,
            context,
            Inventory.class
        );

        return super.positive == inventories.test(Inventory::isEmpty);
    }

    /**
     * A factory for creating {@link PsiIsInventoryEmptyConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsInventoryEmptyCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiIsInventoryEmptyCondition create(
            @NotNull PsiElement<?> inventories,
            boolean positive,
            int lineNumber
        ) {
            return new PsiIsInventoryEmptyConditionImpl(inventories, positive, lineNumber);
        }
    }
}
