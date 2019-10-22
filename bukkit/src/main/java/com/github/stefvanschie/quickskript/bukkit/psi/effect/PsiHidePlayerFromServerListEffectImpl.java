package com.github.stefvanschie.quickskript.bukkit.psi.effect;

import com.github.stefvanschie.quickskript.bukkit.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiHidePlayerFromServerListEffect;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.server.ServerListPingEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

/**
 * Hides a player from the server list
 *
 * @since 0.1.0
 */
public class PsiHidePlayerFromServerListEffectImpl extends PsiHidePlayerFromServerListEffect {

    /**
     * Creates a new element with the given line number
     *
     * @param player     the player to hide from the server list, see {@link #player}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiHidePlayerFromServerListEffectImpl(@NotNull PsiElement<?> player, int lineNumber) {
        super(player, lineNumber);
    }

    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Code needs to be run from an event trigger", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof ServerListPingEvent)) {
            throw new ExecutionException("Code needs to be run from a server list ping trigger", lineNumber);
        }

        Player player = this.player.execute(context, Player.class);
        Iterator<Player> iterator = ((ServerListPingEvent) event).iterator();

        while (iterator.hasNext()) {
            if (!iterator.next().equals(player)) {
                continue;
            }

            iterator.remove();
            break;
        }

        return null;
    }

    /**
     * A factory for creating {@link PsiHidePlayerFromServerListEffectImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiHidePlayerFromServerListEffect.Factory {

        @NotNull
        @Override
        public PsiHidePlayerFromServerListEffect create(@NotNull PsiElement<?> player, int lineNumber) {
            return new PsiHidePlayerFromServerListEffectImpl(player, lineNumber);
        }
    }
}
