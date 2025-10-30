package com.github.stefvanschie.quickskript.paper.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiPlayerListHeaderExpression;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the header of the player list for a player. This cannot be pre-computed, since this may change during gameplay.
 *
 * @since 0.1.0
 */
public class PsiPlayerListHeaderExpressionImpl extends PsiPlayerListHeaderExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to get the header for
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiPlayerListHeaderExpressionImpl(@NotNull PsiElement<?> player, int lineNumber) {
        super(player, lineNumber);
    }

    @Nullable
    @Contract(pure = true)
    @Override
    protected String executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return player.execute(environment, context, Player.class).getPlayerListHeader();
    }

    @Override
    public void delete(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        player.execute(environment, context, Player.class).setPlayerListHeader("");
    }

    @Override
    public void reset(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        delete(environment, context);
    }

    @Override
    public void set(@Nullable SkriptRunEnvironment environment, @Nullable Context context, @NotNull PsiElement<?> object) {
        player.execute(environment, context, Player.class).setPlayerListHeader(object.execute(environment, context, String.class));
    }

    /**
     * A factory for creating {@link PsiPlayerListHeaderExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiPlayerListHeaderExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiPlayerListHeaderExpression create(@NotNull PsiElement<?> player, int lineNumber) {
            return new PsiPlayerListHeaderExpressionImpl(player, lineNumber);
        }
    }
}
