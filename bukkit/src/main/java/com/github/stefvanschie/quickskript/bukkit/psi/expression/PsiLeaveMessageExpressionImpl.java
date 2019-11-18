package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.bukkit.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiLeaveMessageExpression;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the leave message from a player leave event
 *
 * @since 0.1.0
 */
public class PsiLeaveMessageExpressionImpl extends PsiLeaveMessageExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiLeaveMessageExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Text executeImpl(@Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Leave message expression can only be executed from an event", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof PlayerQuitEvent)) {
            throw new ExecutionException("Leave message expression can only be executed from a leave event",
                lineNumber);
        }

        return Text.parseNullable(((PlayerQuitEvent) event).getQuitMessage());
    }

    @Override
    public void set(@Nullable Context context, @NotNull PsiElement<?> object) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Leave message expression can only be executed from an event", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof PlayerQuitEvent)) {
            throw new ExecutionException("Leave message expression can only be executed from a leave event",
                lineNumber);
        }

        ((PlayerQuitEvent) event).setQuitMessage(object.execute(context, Text.class).toString());
    }

    /**
     * A factory for creating {@link PsiLeaveMessageExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiLeaveMessageExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiLeaveMessageExpression create(int lineNumber) {
            return new PsiLeaveMessageExpressionImpl(lineNumber);
        }
    }
}
