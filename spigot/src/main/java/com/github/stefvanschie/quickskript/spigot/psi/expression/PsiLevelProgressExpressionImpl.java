package com.github.stefvanschie.quickskript.spigot.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiLevelProgressExpression;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the progress towards the next level from a player. This cannot be pre-computed, since this may change during
 * gameplay.
 *
 * @since 0.1.0
 */
public class PsiLevelProgressExpressionImpl extends PsiLevelProgressExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to get the level progress of
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiLevelProgressExpressionImpl(@NotNull PsiElement<?> player, int lineNumber) {
        super(player, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Float executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return player.execute(environment, context, Player.class).getExp();
    }

    @Override
    public void add(@Nullable SkriptRunEnvironment environment, @Nullable Context context, @NotNull PsiElement<?> object) {
        Player player = this.player.execute(environment, context, Player.class);
        float exp = player.getExp() + object.execute(environment, context, Number.class).floatValue();

        player.setLevel(player.getLevel() + (int) Math.floor(exp));
        player.setExp(player.getExp() + exp % 1);
    }

    @Override
    public void delete(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        this.player.execute(environment, context, Player.class).setExp(0);
    }

    @Override
    public void remove(@Nullable SkriptRunEnvironment environment, @Nullable Context context, @NotNull PsiElement<?> object) {
        Player player = this.player.execute(environment, context, Player.class);
        float exp = player.getExp() - object.execute(environment, context, Number.class).floatValue();

        player.setLevel(player.getLevel() + (int) Math.floor(exp));
        player.setExp(player.getExp() + exp % 1);
    }

    @Override
    public void reset(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        delete(environment, context);
    }

    @Override
    public void set(@Nullable SkriptRunEnvironment environment, @Nullable Context context, @NotNull PsiElement<?> object) {
        Player player = this.player.execute(environment, context, Player.class);
        float exp = object.execute(environment, context, Number.class).floatValue();

        player.setLevel(player.getLevel() + (int) Math.floor(exp));
        player.setExp(player.getExp() + exp % 1);
    }

    /**
     * A factory for creating {@link PsiLevelProgressExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiLevelProgressExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiLevelProgressExpression create(@NotNull PsiElement<?> player, int lineNumber) {
            return new PsiLevelProgressExpressionImpl(player, lineNumber);
        }
    }
}
