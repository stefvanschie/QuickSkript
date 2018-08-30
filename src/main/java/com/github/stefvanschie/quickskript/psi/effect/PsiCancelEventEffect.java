package com.github.stefvanschie.quickskript.psi.effect;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.context.EventContext;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A psi element for cancelling events
 *
 * @since 0.1.0
 */
public class PsiCancelEventEffect extends PsiElement<Void> {

    /**
     * Constructs a new psi cancel event effect
     *
     * @since 0.1.0
     */
    private PsiCancelEventEffect() {}

    /**
     * {@inheritDoc}
     */
    @Override
    protected Void executeImpl(@Nullable Context context) {
        if (!(context instanceof EventContext))
            throw new IllegalStateException("Code is not being run from an event and thus can't cancel anything.");

        Event event = ((EventContext) context).getEvent();

        if (!(event instanceof Cancellable))
            throw new UnsupportedOperationException("This event cannot be cancelled.");

        ((Cancellable) event).setCancelled(true);

        return null;
    }

    /**
     * A factory for creating psi cancel event effects
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiCancelEventEffect> {

        /**
         * A pattern for matching psi cancel event effects
         */
        private final Pattern PATTERN = Pattern.compile("cancel (?:the )?event");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiCancelEventEffect tryParse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            return new PsiCancelEventEffect();
        }
    }
}
