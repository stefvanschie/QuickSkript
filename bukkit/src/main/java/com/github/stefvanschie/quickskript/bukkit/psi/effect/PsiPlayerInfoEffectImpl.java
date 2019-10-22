package com.github.stefvanschie.quickskript.bukkit.psi.effect;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import com.github.stefvanschie.quickskript.bukkit.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiPlayerInfoEffect;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Hides/Shows player info when pinging the server
 *
 * @since 0.1.0
 */
public class PsiPlayerInfoEffectImpl extends PsiPlayerInfoEffect {

    /**
     * Creates a new element with the given line number
     *
     * @param show       whether the information should be shown or hidden, see {@link #show}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiPlayerInfoEffectImpl(boolean show, int lineNumber) {
        super(show, lineNumber);
    }

    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Code needs to be ran from within an event", lineNumber);
        }

        EventContextImpl eventContext = (EventContextImpl) context;
        Event event = eventContext.getEvent();

        if (!(event instanceof PaperServerListPingEvent)) {
            throw new ExecutionException("Code needs to be ran from within a server list ping event", lineNumber);
        }

        ((PaperServerListPingEvent) event).setHidePlayers(!show);

        return null;
    }

    /**
     * A factory for creating {@link PsiPlayerInfoEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiPlayerInfoEffect.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        protected PsiPlayerInfoEffect create(boolean show, int lineNumber) {
            return new PsiPlayerInfoEffectImpl(show, lineNumber);
        }
    }
}
