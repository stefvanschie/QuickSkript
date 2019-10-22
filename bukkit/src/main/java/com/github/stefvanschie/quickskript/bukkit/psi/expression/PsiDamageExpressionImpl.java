package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.bukkit.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiDamageExpression;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the amount of damage that was done in a damaging event
 *
 * @since 0.1.0
 */
public class PsiDamageExpressionImpl extends PsiDamageExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiDamageExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Double executeImpl(@Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Damage expression can only be called from events", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (event instanceof EntityDamageEvent) {
            return ((EntityDamageEvent) event).getDamage();
        } else if (event instanceof VehicleDamageEvent) {
            return ((VehicleDamageEvent) event).getDamage();
        }

        throw new ExecutionException("Damage expression can only be called from damage events", lineNumber);
    }

    /**
     * A factory for creating {@link PsiDamageExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiDamageExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiDamageExpression create(int lineNumber) {
            return new PsiDamageExpressionImpl(lineNumber);
        }
    }
}
