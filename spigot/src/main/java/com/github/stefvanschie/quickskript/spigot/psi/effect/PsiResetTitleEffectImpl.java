package com.github.stefvanschie.quickskript.spigot.psi.effect;

import com.github.stefvanschie.quickskript.spigot.context.ContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiResetTitleEffect;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Resets a player's title and subtitle
 *
 * @since 0.1.0
 */
public class PsiResetTitleEffectImpl extends PsiResetTitleEffect {

    /**
     * Creates a new element with the given line number
     *
     * @param player     the player to reset the title for, see {@link #player}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiResetTitleEffectImpl(@Nullable PsiElement<?> player, int lineNumber) {
        super(player, lineNumber);
    }

    @Nullable
    @Override
    protected Void executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        Player player;

        if (this.player == null) {
            if (context == null) {
                throw new ExecutionException("Cannot reset title without context", lineNumber);
            }

            CommandSender commandSender = ((ContextImpl) context).getCommandSender();

            if (commandSender instanceof Player) {
                player = (Player) commandSender;
            } else {
                throw new ExecutionException("Unable to find a player to reset the title for", lineNumber);
            }
        } else {
            player = this.player.execute(environment, context, Player.class);
        }

        player.resetTitle();

        return null;
    }

    /**
     * A factory to create {@link PsiResetTitleEffectImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiResetTitleEffect.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiResetTitleEffect create(@Nullable PsiElement<?> player, int lineNumber) {
            return new PsiResetTitleEffectImpl(player, lineNumber);
        }
    }
}
