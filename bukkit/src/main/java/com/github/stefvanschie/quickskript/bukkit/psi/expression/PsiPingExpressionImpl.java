package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiPingExpression;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the ping of the player. This cannot be pre-computed, because this may change during gameplay.
 *
 * @since 0.1.0
 */
public class PsiPingExpressionImpl extends PsiPingExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to get the ping from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiPingExpressionImpl(@NotNull PsiElement<?> player, int lineNumber) {
        super(player, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Integer executeImpl(@Nullable Context context) {
        return player.execute(context, Player.class).spigot().getPing();
    }

    /**
     * A factory for creating {@link PsiPingExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiPingExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiPingExpression create(@NotNull PsiElement<?> player, int lineNumber) {
            return new PsiPingExpressionImpl(player, lineNumber);
        }
    }
}
