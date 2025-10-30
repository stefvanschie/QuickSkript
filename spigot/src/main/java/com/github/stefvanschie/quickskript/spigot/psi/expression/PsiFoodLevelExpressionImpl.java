package com.github.stefvanschie.quickskript.spigot.psi.expression;

import com.github.stefvanschie.quickskript.spigot.context.ContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiFoodLevelExpression;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the amount of food the player has between 0 and 10. This cannot be pre computed, since the food level of a
 * player may change during gameplay.
 *
 * @since 0.1.0
 */
public class PsiFoodLevelExpressionImpl extends PsiFoodLevelExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to get the food level from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiFoodLevelExpressionImpl(@Nullable PsiElement<?> player, int lineNumber) {
        super(player, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Double executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return forceGetPlayer(environment, context).getFoodLevel() / 2.0;
    }

    @Override
    public void add(@Nullable SkriptRunEnvironment environment, @Nullable Context context, @NotNull PsiElement<?> object) {
        Player player = forceGetPlayer(environment, context);

        player.setFoodLevel(player.getFoodLevel() + (int) (object.execute(environment, context, Number.class).doubleValue() * 2));
    }

    @Override
    public void delete(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        forceGetPlayer(environment, context).setFoodLevel(0);
    }

    @Override
    public void remove(@Nullable SkriptRunEnvironment environment, @Nullable Context context, @NotNull PsiElement<?> object) {
        Player player = forceGetPlayer(environment, context);

        player.setFoodLevel(player.getFoodLevel() - (int) (object.execute(environment, context, Number.class).doubleValue() * 2));
    }

    @Override
    public void reset(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        forceGetPlayer(environment, context).setFoodLevel(20);
    }

    @Override
    public void set(@Nullable SkriptRunEnvironment environment, @Nullable Context context, @NotNull PsiElement<?> object) {
        forceGetPlayer(environment, context).setFoodLevel((int) (object.execute(environment, context, Number.class).doubleValue() * 2));
    }

    /**
     * Forcefully gets the {@link Player} to be used in this expression. If no {@link Player} is available, this throws
     * an {@link ExecutionException}.
     *
     * @param context the context
     * @return the player
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    private Player forceGetPlayer(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        Player player = null;

        if (this.player == null && context != null) {
            CommandSender commandSender = ((ContextImpl) context).getCommandSender();

            if (commandSender instanceof Player) {
                player = (Player) commandSender;
            }
        } else if (this.player != null) {
            player = this.player.execute(environment, context, Player.class);
        }

        if (player == null) {
            throw new ExecutionException("Could not find player to get the food level from", lineNumber);
        }

        return player;
    }

    /**
     * A factory for creating {@link PsiFoodLevelExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiFoodLevelExpression.Factory {

        @NotNull
        @Override
        public PsiFoodLevelExpression create(@Nullable PsiElement<?> player, int lineNumber) {
            return new PsiFoodLevelExpressionImpl(player, lineNumber);
        }
    }
}
