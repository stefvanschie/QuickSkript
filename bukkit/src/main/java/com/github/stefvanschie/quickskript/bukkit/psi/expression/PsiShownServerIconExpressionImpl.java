package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import com.github.stefvanschie.quickskript.bukkit.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiShownServerIconExpression;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.util.CachedServerIcon;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the server icon shown in a ping event
 *
 * @since 0.1.0
 */
public class PsiShownServerIconExpressionImpl extends PsiShownServerIconExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiShownServerIconExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    @Nullable
    @Contract(pure = true)
    @Override
    protected CachedServerIcon executeImpl(@Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("The shown server icon can only be retrieved in an event", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof PaperServerListPingEvent)) {
            throw new ExecutionException("The shown server icon can only be retrieved in a ping event", lineNumber);
        }

        return ((PaperServerListPingEvent) event).getServerIcon();
    }

    @Override
    public void reset(@Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("The shown server icon can only be retrieved in an event", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof PaperServerListPingEvent)) {
            throw new ExecutionException("The shown server icon can only be retrieved in a ping event", lineNumber);
        }

        ((PaperServerListPingEvent) event).setServerIcon(Bukkit.getServerIcon());
    }

    @Override
    public void set(@Nullable Context context, @NotNull PsiElement<?> object) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("The shown server icon can only be retrieved in an event", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof PaperServerListPingEvent)) {
            throw new ExecutionException("The shown server icon can only be retrieved in a ping event", lineNumber);
        }

        ((PaperServerListPingEvent) event).setServerIcon(object.execute(context, CachedServerIcon.class));
    }

    /**
     * A factory for creating {@link PsiShownServerIconExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiShownServerIconExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiShownServerIconExpression create(int lineNumber) {
            return new PsiShownServerIconExpressionImpl(lineNumber);
        }
    }
}
