package com.github.stefvanschie.quickskript.spigot.psi.expression;

import com.github.stefvanschie.quickskript.spigot.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiInitiatorInventoryExpression;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the inventory involved in inventory-related events.
 *
 * @since 0.1.0
 */
public class PsiInitiatorInventoryExpressionImpl extends PsiInitiatorInventoryExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiInitiatorInventoryExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Inventory executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        if (!(context instanceof EventContextImpl)) {
            throw new ExecutionException("Unable to get initiatory inventory outside events", super.lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof InventoryMoveItemEvent)) {
            throw new ExecutionException(
                "Unable to get initiatory inventory outside inventory events",
                super.lineNumber
            );
        }

        return ((InventoryMoveItemEvent) event).getInitiator();
    }

    /**
     * A factory for creating {@link PsiInitiatorInventoryExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiInitiatorInventoryExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiInitiatorInventoryExpression create(int lineNumber) {
            return new PsiInitiatorInventoryExpressionImpl(lineNumber);
        }
    }
}
