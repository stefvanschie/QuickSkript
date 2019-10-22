package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiHiddenPlayersExpression;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Gets the players that are currently hidden for the player. This cannot be pre-computed, since this may change during
 * gameplay.
 *
 * @since 0.1.0
 */
public class PsiHiddenPlayersExpressionImpl extends PsiHiddenPlayersExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to get their hidden players from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiHiddenPlayersExpressionImpl(@NotNull PsiElement<?> player, int lineNumber) {
        super(player, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Collection<?> executeImpl(@Nullable Context context) {
        return player.execute(context, Player.class).spigot().getHiddenPlayers();
    }

    /**
     * A factory for creating {@link PsiHiddenPlayersExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiHiddenPlayersExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiHiddenPlayersExpression create(@NotNull PsiElement<?> player, int lineNumber) {
            return new PsiHiddenPlayersExpressionImpl(player, lineNumber);
        }
    }
}
