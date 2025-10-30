package com.github.stefvanschie.quickskript.spigot.psi.expression;

import com.github.stefvanschie.quickskript.spigot.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiFakeOnlinePlayerCountExpression;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
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
    protected Integer executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
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
