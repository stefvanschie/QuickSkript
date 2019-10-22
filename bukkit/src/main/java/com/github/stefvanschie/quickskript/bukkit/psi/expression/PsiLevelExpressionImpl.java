package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiLevelExpression;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the experience level from a player. This cannot be pre-computed, since this may change during gameplay.
 *
 * @since 0.1.0
 */
public class PsiLevelExpressionImpl extends PsiLevelExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to get the level from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiLevelExpressionImpl(@NotNull PsiElement<?> player, int lineNumber) {
        super(player, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Integer executeImpl(@Nullable Context context) {
        return this.player.execute(context, Player.class).getLevel();
    }

    @Override
    public void add(@Nullable Context context, @NotNull PsiElement<?> object) {
        Player player = this.player.execute(context, Player.class);

        player.setLevel(player.getLevel() + object.execute(context, Integer.class));
    }

    @Override
    public void delete(@Nullable Context context) {
        this.player.execute(context, Player.class).setLevel(0);
    }

    @Override
    public void remove(@Nullable Context context, @NotNull PsiElement<?> object) {
        Player player = this.player.execute(context, Player.class);

        player.setLevel(player.getLevel() - object.execute(context, Integer.class));
    }

    @Override
    public void reset(@Nullable Context context) {
        delete(null);
    }

    @Override
    public void set(@Nullable Context context, @NotNull PsiElement<?> object) {
        this.player.execute(context, Player.class).setLevel(object.execute(context, Integer.class));
    }

    /**
     * A factory for creating {@link PsiLevelExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiLevelExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiLevelExpression create(@NotNull PsiElement<?> player, int lineNumber) {
            return new PsiLevelExpressionImpl(player, lineNumber);
        }
    }
}
