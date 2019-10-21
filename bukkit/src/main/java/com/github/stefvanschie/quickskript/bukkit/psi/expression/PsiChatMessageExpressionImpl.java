package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.bukkit.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiChatMessageExpression;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.bukkit.event.Event;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the message send in a chat event
 *
 * @since 0.1.0
 */
public class PsiChatMessageExpressionImpl extends PsiChatMessageExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiChatMessageExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Contract(pure = true)
    @Override
    @SuppressWarnings("deprecation")
    protected Text executeImpl(@Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Chat message can only be retrieved from events", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (event instanceof AsyncPlayerChatEvent) {
            return Text.parseLiteral(((AsyncPlayerChatEvent) event).getMessage());
        }

        if (event instanceof PlayerChatEvent) {
            return Text.parseLiteral(((PlayerChatEvent) event).getMessage());
        }

        throw new ExecutionException("Chat message can only be retrieved from chat events", lineNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("deprecation")
    public void set(@Nullable Context context, @NotNull PsiElement<?> object) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Chat message can only be retrieved from events", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();
        String message = object.execute(context, Text.class).toString();

        if (event instanceof AsyncPlayerChatEvent) {
            ((AsyncPlayerChatEvent) event).setMessage(message);
        }

        if (event instanceof PlayerChatEvent) {
            ((PlayerChatEvent) event).setMessage(message);
        }

        throw new ExecutionException("Chat message can only be retrieved from chat events", lineNumber);
    }

    /**
     * A factory for creating {@link PsiChatMessageExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiChatMessageExpression.Factory {

        /**
         * {@inheritDoc}
         */
        @NotNull
        @Contract(pure = true)
        @Override
        public PsiChatMessageExpression create(int lineNumber) {
            return new PsiChatMessageExpressionImpl(lineNumber);
        }
    }
}
