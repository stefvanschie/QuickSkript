package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.bukkit.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiJoinMessageExpression;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the join message in a join event
 *
 * @since 0.1.0
 */
public class PsiJoinMessageExpressionImpl extends PsiJoinMessageExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiJoinMessageExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Text executeImpl(@Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Join message can only be retrieved from an event", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof PlayerJoinEvent)) {
            throw new ExecutionException("Join message can only be retrieved from a join event", lineNumber);
        }

        return Text.parseNullable(((PlayerJoinEvent) event).getJoinMessage());
    }

    @Override
    public void set(@Nullable Context context, @NotNull PsiElement<?> object) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Join message can only be retrieved from an event", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof PlayerJoinEvent)) {
            throw new ExecutionException("Join message can only be retrieved from a join event", lineNumber);
        }

        ((PlayerJoinEvent) event).setJoinMessage(object.execute(context, Text.class).toString());
    }

    /**
     * A factory for creating {@link PsiJoinMessageExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiJoinMessageExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiJoinMessageExpression create(int lineNumber) {
            return new PsiJoinMessageExpressionImpl(lineNumber);
        }
    }
}
