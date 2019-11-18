package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.bukkit.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiDeathMessageExpression;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.bukkit.event.Event;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the death message of a death event
 *
 * @since 0.1.0
 */
public class PsiDeathMessageExpressionImpl extends PsiDeathMessageExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiDeathMessageExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Text executeImpl(@Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Death message expression can only be executed from an event", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof PlayerDeathEvent)) {
            throw new ExecutionException("Death message expression can only be executed from a death event",
                lineNumber);
        }

        return Text.parseNullable(((PlayerDeathEvent) event).getDeathMessage());
    }

    @Override
    public void set(@Nullable Context context, @NotNull PsiElement<?> object) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Death message expression can only be executed from an event", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof PlayerDeathEvent)) {
            throw new ExecutionException("Death message expression can only be executed from a death event",
                lineNumber);
        }

        ((PlayerDeathEvent) event).setDeathMessage(object.execute(context, Text.class).toString());
    }

    /**
     * A factory for creating {@link PsiDeathMessageExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiDeathMessageExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiDeathMessageExpression create(int lineNumber) {
            return new PsiDeathMessageExpressionImpl(lineNumber);
        }
    }
}
