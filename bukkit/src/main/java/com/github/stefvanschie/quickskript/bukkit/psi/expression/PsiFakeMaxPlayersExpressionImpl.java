package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.bukkit.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiFakeMaxPlayersExpression;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.server.ServerListPingEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The fake amount of maximum players from a ping event. This cannot be pre-computed, since this may change during
 * gameplay.
 *
 * @since 0.1.0
 */
public class PsiFakeMaxPlayersExpressionImpl extends PsiFakeMaxPlayersExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiFakeMaxPlayersExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Integer executeImpl(@Nullable Context context) {
        return forceGetServerListPingEvent(context).getMaxPlayers();
    }

    @Override
    public void add(@Nullable Context context, @NotNull PsiElement<?> object) {
        ServerListPingEvent serverListPingEvent = forceGetServerListPingEvent(context);
        int newMaxPlayers = serverListPingEvent.getMaxPlayers() + object.execute(context, Number.class).intValue();

        serverListPingEvent.setMaxPlayers(newMaxPlayers);
    }

    @Override
    public void delete(@Nullable Context context) {
        forceGetServerListPingEvent(context).setMaxPlayers(Bukkit.getMaxPlayers());
    }

    @Override
    public void remove(@Nullable Context context, @NotNull PsiElement<?> object) {
        ServerListPingEvent serverListPingEvent = forceGetServerListPingEvent(context);
        int newMaxPlayers = serverListPingEvent.getMaxPlayers() - object.execute(context, Number.class).intValue();

        serverListPingEvent.setMaxPlayers(newMaxPlayers);
    }

    @Override
    public void reset(@Nullable Context context) {
        delete(context);
    }

    @Override
    public void set(@Nullable Context context, @NotNull PsiElement<?> object) {
        forceGetServerListPingEvent(context).setMaxPlayers(object.execute(context, Number.class).intValue());
    }

    /**
     * Gets the {@link ServerListPingEvent} from a {@link Context}.
     *
     * @param context the context to retrieve the event from
     * @return the {@link ServerListPingEvent}
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    private ServerListPingEvent forceGetServerListPingEvent(@Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Fake max players expression can only be called from an event", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof ServerListPingEvent)) {
            throw new ExecutionException("Fake max players expression can only be called from a ping event",
                lineNumber);
        }

        return (ServerListPingEvent) event;
    }

    /**
     * A factory for creating {@link PsiFakeMaxPlayersExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiFakeMaxPlayersExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiFakeMaxPlayersExpression create(int lineNumber) {
            return new PsiFakeMaxPlayersExpressionImpl(lineNumber);
        }
    }
}
