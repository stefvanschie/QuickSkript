package com.github.stefvanschie.quickskript.paper.psi.expression;

import com.github.stefvanschie.quickskript.paper.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiChatMessageExpression;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
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
    protected String executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Chat message can only be retrieved from events", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (event instanceof AsyncPlayerChatEvent) {
            return ((AsyncPlayerChatEvent) event).getMessage();
        }

        if (event instanceof PlayerChatEvent) {
            return ((PlayerChatEvent) event).getMessage();
        }

        throw new ExecutionException("Chat message can only be retrieved from chat events", lineNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("deprecation")
    public void set(@Nullable SkriptRunEnvironment environment, @Nullable Context context, @NotNull PsiElement<?> object) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Chat message can only be retrieved from events", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();
        String message = object.execute(environment, context, String.class);

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
