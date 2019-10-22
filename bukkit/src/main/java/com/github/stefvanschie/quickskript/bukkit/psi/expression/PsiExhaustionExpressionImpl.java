package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiExhaustionExpression;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the amount of exhaustion the player has
 *
 * @since 0.1.0
 */
public class PsiExhaustionExpressionImpl extends PsiExhaustionExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to get the exhaustion from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiExhaustionExpressionImpl(@NotNull PsiElement<?> player, int lineNumber) {
        super(player, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Float executeImpl(@Nullable Context context) {
        return player.execute(context, Player.class).getExhaustion();
    }

    @Override
    public void add(@Nullable Context context, @NotNull PsiElement<?> object) {
        Player player = this.player.execute(context, Player.class);

        player.setExhaustion(player.getExhaustion() + object.execute(context, Number.class).floatValue());
    }

    @Override
    public void delete(@Nullable Context context) {
        player.execute(context, Player.class).setExhaustion(0);
    }

    @Override
    public void remove(@Nullable Context context, @NotNull PsiElement<?> object) {
        Player player = this.player.execute(context, Player.class);

        player.setExhaustion(player.getExhaustion() - object.execute(context, Number.class).floatValue());
    }

    @Override
    public void removeAll(@Nullable Context context, @NotNull PsiElement<?> object) {
        player.execute(context, Player.class).setExhaustion(0);
    }

    @Override
    public void reset(@Nullable Context context) {
        player.execute(context, Player.class).setExhaustion(0);
    }

    @Override
    public void set(@Nullable Context context, @NotNull PsiElement<?> object) {
        player.execute(context, Player.class).setExhaustion(object.execute(context, Number.class).floatValue());
    }

    /**
     * A factory for creating {@link PsiExhaustionExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiExhaustionExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiExhaustionExpression create(@NotNull PsiElement<?> player, int lineNumber) {
            return new PsiExhaustionExpressionImpl(player, lineNumber);
        }
    }
}
