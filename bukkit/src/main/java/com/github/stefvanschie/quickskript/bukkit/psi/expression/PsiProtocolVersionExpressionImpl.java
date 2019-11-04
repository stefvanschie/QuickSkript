package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import com.github.stefvanschie.quickskript.bukkit.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiProtocolVersionExpression;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the protocol version from a ping event. This cannot be pre-computed, since this may change during gameplay.
 *
 * @since 0.1.0
 */
public class PsiProtocolVersionExpressionImpl extends PsiProtocolVersionExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiProtocolVersionExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Integer executeImpl(@Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Protocol version expression can only be executed from an event", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof PaperServerListPingEvent)) {
            throw new ExecutionException("Protocol version expression can only be executed from a ping event",
                lineNumber);
        }

        return ((PaperServerListPingEvent) event).getProtocolVersion();
    }

    @Override
    public void set(@Nullable Context context, @NotNull PsiElement<?> object) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Protocol version expression can only be executed from an event", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof PaperServerListPingEvent)) {
            throw new ExecutionException("Protocol version expression can only be executed from a ping event",
                lineNumber);
        }

        ((PaperServerListPingEvent) event).setProtocolVersion(object.execute(context, Integer.class));
    }

    /**
     * A factory for creating {@link PsiProtocolVersionExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiProtocolVersionExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiProtocolVersionExpression create(int lineNumber) {
            return new PsiProtocolVersionExpressionImpl(lineNumber);
        }
    }
}
