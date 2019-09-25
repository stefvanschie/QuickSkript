package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import com.github.stefvanschie.quickskript.bukkit.context.EventContextImpl;
import com.github.stefvanschie.quickskript.bukkit.util.Platform;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiIPExpression;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerLoginEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Gets the IP from a player. This cannot be pre computed, since players may join and leave during gameplay.
 *
 * @since 0.1.0
 */
public class PsiIPExpressionImpl extends PsiIPExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to get the IP from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIPExpressionImpl(@Nullable PsiElement<?> player, int lineNumber) {
        super(player, lineNumber);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Contract(pure = true)
    @Override
    protected Text executeImpl(@Nullable Context context) {
        InetAddress inetAddress;

        if (player == null) {
            if (!(context instanceof EventContext)) {
                throw new ExecutionException("This expression can only be executed from an event", lineNumber);
            }

            Event event = ((EventContextImpl) context).getEvent();

            if (event instanceof PlayerLoginEvent) {
                inetAddress = ((PlayerLoginEvent) event).getAddress();
            } else if (Platform.getPlatform().isAvailable(Platform.PAPER)) {
                inetAddress = Helper.executeImplHelper(context, lineNumber);
            } else {
                throw new ExecutionException("You need Paper to execute this expression from a ping event", lineNumber);
            }
        } else {
            InetSocketAddress address = player.execute(context, Player.class).getAddress();

            if (address == null) {
                throw new ExecutionException("Could not find address for player", lineNumber);
            }

            inetAddress = address.getAddress();
        }

        return Text.parseLiteral(inetAddress.getHostAddress());
    }

    /**
     * A helper class that ensures Paper specific classes are only loaded on Paper (at least I hope so, class loader
     * resolving is kinda weird).
     *
     * @since 0.1.0
     */
    private static class Helper {

        /**
         * A helper method that ensures Paper specific classes are only loaded on Paper
         *
         * @param context the context
         * @return the address
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        private static InetAddress executeImplHelper(@Nullable Context context, int lineNumber) {
            if (!(context instanceof EventContext)) {
                throw new ExecutionException("This expression can only be executed from an event", lineNumber);
            }

            Event event = ((EventContextImpl) context).getEvent();

            if (event instanceof PaperServerListPingEvent) {
                //TODO isn't ServerListPingEvent enough? that way it's not Paper-specific
                return ((PaperServerListPingEvent) event).getAddress();
            }

            throw new ExecutionException("This expression can only be used in login and ping events", lineNumber);
        }
    }

    /**
     * A factory for creating {@link PsiIPExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIPExpression.Factory {

        /**
         * {@inheritDoc}
         */
        @NotNull
        @Contract(pure = true)
        @Override
        public PsiIPExpression create(@Nullable PsiElement<?> player, int lineNumber) {
            return new PsiIPExpressionImpl(player, lineNumber);
        }
    }
}
