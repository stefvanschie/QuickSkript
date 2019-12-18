package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.bukkit.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiTamerExpression;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityTameEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the tamer from a tame event
 *
 * @since 0.1.0
 */
public class PsiTamerExpressionImpl extends PsiTamerExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiTamerExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected AnimalTamer executeImpl(@Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Tamer expression can only be used in events", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof EntityTameEvent)) {
            throw new ExecutionException("Tamer expression can only be used in tame events", lineNumber);
        }

        return ((EntityTameEvent) event).getOwner();
    }

    /**
     * A factory for creating {@link PsiTamerExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiTamerExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiTamerExpression create(int lineNumber) {
            return new PsiTamerExpressionImpl(lineNumber);
        }
    }
}
