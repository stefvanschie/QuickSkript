package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiExhaustionExpression;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
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
    protected Float executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return player.execute(environment, context, Player.class).getExhaustion();
    }

    @Override
    public void add(@Nullable SkriptRunEnvironment environment, @Nullable Context context, @NotNull PsiElement<?> object) {
        Player player = this.player.execute(environment, context, Player.class);

        player.setExhaustion(player.getExhaustion() + object.execute(environment, context, Number.class).floatValue());
    }

    @Override
    public void delete(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        player.execute(environment, context, Player.class).setExhaustion(0);
    }

    @Override
    public void remove(@Nullable SkriptRunEnvironment environment, @Nullable Context context, @NotNull PsiElement<?> object) {
        Player player = this.player.execute(environment, context, Player.class);

        player.setExhaustion(player.getExhaustion() - object.execute(environment, context, Number.class).floatValue());
    }

    @Override
    public void removeAll(@Nullable SkriptRunEnvironment environment, @Nullable Context context, @NotNull PsiElement<?> object) {
        player.execute(environment, context, Player.class).setExhaustion(0);
    }

    @Override
    public void reset(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        player.execute(environment, context, Player.class).setExhaustion(0);
    }

    @Override
    public void set(@Nullable SkriptRunEnvironment environment, @Nullable Context context, @NotNull PsiElement<?> object) {
        player.execute(environment, context, Player.class).setExhaustion(object.execute(environment, context, Number.class).floatValue());
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
