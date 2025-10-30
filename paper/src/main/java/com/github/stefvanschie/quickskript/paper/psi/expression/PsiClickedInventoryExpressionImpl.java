package com.github.stefvanschie.quickskript.paper.psi.expression;

import com.github.stefvanschie.quickskript.paper.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiClickedInventoryExpression;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the inventory that was clicked.
 *
 * @since 0.1.0
 */
public class PsiClickedInventoryExpressionImpl extends PsiClickedInventoryExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiClickedInventoryExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    @Nullable
    @Contract(pure = true)
    @Override
    protected Inventory executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        if (!(context instanceof EventContextImpl)) {
            throw new ExecutionException("Unable to get clicked inventory outside events", super.lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof InventoryClickEvent)) {
            throw new ExecutionException(
                "Unable to get clicked inventory outside inventory events",
                super.lineNumber
            );
        }

        return ((InventoryClickEvent) event).getClickedInventory();
    }

    /**
     * A factory for creating {@link PsiClickedInventoryExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiClickedInventoryExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiClickedInventoryExpression create(int lineNumber) {
            return new PsiClickedInventoryExpressionImpl(lineNumber);
        }
    }
}
