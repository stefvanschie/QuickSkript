package com.github.stefvanschie.quickskript.bukkit.psi.effect;

import com.github.stefvanschie.quickskript.bukkit.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiCancelEventEffect;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A psi element for cancelling events
 *
 * @since 0.1.0
 */
public class PsiCancelEventEffectImpl extends PsiCancelEventEffect {

    private PsiCancelEventEffectImpl(int lineNumber) {
        super(lineNumber);
    }

    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Code is not being run from an event and thus can't cancel anything.",
                lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof Cancellable)) {
            throw new ExecutionException("This event cannot be cancelled.", lineNumber);
        }

        ((Cancellable) event).setCancelled(true);

        return null;
    }

    /**
     * A factory for creating psi cancel event effects
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiCancelEventEffect.Factory {

        @NotNull
        @Override
        public PsiCancelEventEffectImpl create(int lineNumber) {
            return new PsiCancelEventEffectImpl(lineNumber);
        }
    }
}
