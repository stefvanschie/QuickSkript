package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import com.github.stefvanschie.quickskript.bukkit.context.EventContextImpl;
import com.github.stefvanschie.quickskript.bukkit.util.Platform;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiFakeOnlinePlayerCountExpression;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.server.ServerListPingEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the fake player count shown in ping events
 *
 * @since 0.1.0
 */
public class PsiFakeOnlinePlayerCountExpressionImpl extends PsiFakeOnlinePlayerCountExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiFakeOnlinePlayerCountExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Integer executeImpl(@Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Fake online player count expression can only be executed from events",
                lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof ServerListPingEvent)) {
            throw new ExecutionException("Fake online player count expression can only be executed from ping events",
                lineNumber);
        }

        return ((ServerListPingEvent) event).getNumPlayers();
    }

    @Override
    public void add(@Nullable Context context, @NotNull PsiElement<?> object) {
        PaperServerListPingEvent pingEvent = forceGetPingEvent(context);

        pingEvent.setNumPlayers(pingEvent.getNumPlayers() + object.execute(context, Number.class).intValue());
    }

    @Override
    public void delete(@Nullable Context context) {
        forceGetPingEvent(context).setNumPlayers(Bukkit.getOnlinePlayers().size());
    }

    @Override
    public void remove(@Nullable Context context, @NotNull PsiElement<?> object) {
        PaperServerListPingEvent pingEvent = forceGetPingEvent(context);

        pingEvent.setNumPlayers(pingEvent.getNumPlayers() - object.execute(context, Number.class).intValue());
    }

    @Override
    public void reset(@Nullable Context context) {
        delete(context);
    }

    @Override
    public void set(@Nullable Context context, @NotNull PsiElement<?> object) {
        forceGetPingEvent(context).setNumPlayers(object.execute(context, Number.class).intValue());
    }

    /**
     * Forcefully gets the ping event from the specified context.
     *
     * @param context the context specified
     * @return the ping event
     * @since 0.1.0
     * @throws ExecutionException if context is null; if the current platform doesn't include Paper; if the context
     * isn't event-based; or if the event from the context isn't a ping event.
     */
    @NotNull
    @Contract(value = "null -> fail", pure = true)
    private PaperServerListPingEvent forceGetPingEvent(@Nullable Context context) {
        if (!Platform.getPlatform().isAvailable(Platform.PAPER)) {
            throw new ExecutionException("Fake online player count expression can only be executed on Paper",
                lineNumber);
        }

        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Fake online player count expression can only be executed from events",
                lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof PaperServerListPingEvent)) {
            throw new ExecutionException("Fake online player count expression can only be executed from ping events",
                lineNumber);
        }

        return (PaperServerListPingEvent) event;
    }

    /**
     * A factory for creating {@link PsiFakeOnlinePlayerCountExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiFakeOnlinePlayerCountExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiFakeOnlinePlayerCountExpression create(int lineNumber) {
            return new PsiFakeOnlinePlayerCountExpressionImpl(lineNumber);
        }
    }
}
