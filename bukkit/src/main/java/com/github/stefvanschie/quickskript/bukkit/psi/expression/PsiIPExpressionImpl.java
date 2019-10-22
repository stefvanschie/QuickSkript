package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.bukkit.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiIPExpression;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;
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
            } else if (event instanceof ServerListPingEvent) {
                inetAddress = ((ServerListPingEvent) event).getAddress();
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
     * A factory for creating {@link PsiIPExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIPExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiIPExpression create(@Nullable PsiElement<?> player, int lineNumber) {
            return new PsiIPExpressionImpl(player, lineNumber);
        }
    }
}
