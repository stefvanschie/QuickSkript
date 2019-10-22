package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.bukkit.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiFinalDamageExpression;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the final damage from a damage event
 *
 * @since 0.1.0
 */
public class PsiFinalDamageExpressionImpl extends PsiFinalDamageExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiFinalDamageExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Double executeImpl(@Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Expression can only be executed from events", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof EntityDamageEvent)) {
            throw new ExecutionException("Expression can only be executed from entity damage event", lineNumber);
        }

        return ((EntityDamageEvent) event).getFinalDamage();
    }

    /**
     * A factory for creating {@link PsiFinalDamageExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiFinalDamageExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiFinalDamageExpression create(int lineNumber) {
            return new PsiFinalDamageExpressionImpl(lineNumber);
        }
    }
}
