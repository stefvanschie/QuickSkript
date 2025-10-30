package com.github.stefvanschie.quickskript.paper.psi.expression;

import com.github.stefvanschie.quickskript.paper.context.ContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiSaturationExpression;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the player's saturation. This cannot be pre-computed, since this may change during gameplay.
 *
 * @since 0.1.0
 */
public class PsiSaturationExpressionImpl extends PsiSaturationExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to get the saturation from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiSaturationExpressionImpl(@Nullable PsiElement<?> player, int lineNumber) {
        super(player, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Float executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return forceGetPlayer(environment, context).getSaturation();
    }

    @Override
    public void add(@Nullable SkriptRunEnvironment environment, @Nullable Context context, @NotNull PsiElement<?> object) {
        Player player = forceGetPlayer(environment, context);

        player.setSaturation(player.getSaturation() + object.execute(environment, context, Number.class).floatValue());
    }

    @Override
    public void delete(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        forceGetPlayer(environment, context).setSaturation(0);
    }

    @Override
    public void remove(@Nullable SkriptRunEnvironment environment, @Nullable Context context, @NotNull PsiElement<?> object) {
        Player player = forceGetPlayer(environment, context);

        player.setSaturation(player.getSaturation() - object.execute(environment, context, Number.class).floatValue());
    }

    @Override
    public void reset(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        delete(environment, context);
    }

    @Override
    public void set(@Nullable SkriptRunEnvironment environment, @Nullable Context context, @NotNull PsiElement<?> object) {
        forceGetPlayer(environment, context).setSaturation(object.execute(environment, context, Number.class).floatValue());
    }

    /**
     * Derives a player from both {@link #player} and the provided context. This will throw an
     * {@link ExecutionException} when no player could be found.
     *
     * @param environment the environment for looking for the player
     * @param context the context for looking for the player
     * @return the player
     * @since 0.1.0
     * @throws ExecutionException when the player can't be found
     */
    @NotNull
    @Contract(pure = true)
    private Player forceGetPlayer(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        if (this.player == null && context != null) {
            CommandSender sender = ((ContextImpl) context).getCommandSender();

            if (sender instanceof Player) {
                return (Player) sender;
            }

            throw new ExecutionException("Can only get saturation from players", lineNumber);
        }

        if (this.player != null) {
            return this.player.execute(environment, context, Player.class);
        }

        throw new ExecutionException("Implicit player can only be acquired with context", lineNumber);
    }

    /**
     * A factory for creating {@link PsiSaturationExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiSaturationExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiSaturationExpression create(@Nullable PsiElement<?> player, int lineNumber) {
            return new PsiSaturationExpressionImpl(player, lineNumber);
        }
    }
}
