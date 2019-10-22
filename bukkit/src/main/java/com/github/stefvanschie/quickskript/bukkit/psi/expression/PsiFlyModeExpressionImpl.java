package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiFlyModeExpression;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the fly mode for a player. This cannot be pre computed, since the fly mode of a player may change during game
 * play.
 *
 * @since 0.1.0
 */
public class PsiFlyModeExpressionImpl extends PsiFlyModeExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to get the fly mode from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiFlyModeExpressionImpl(@NotNull PsiElement<?> player, int lineNumber) {
        super(player, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        return player.execute(context, Player.class).getAllowFlight();
    }

    @Override
    public void reset(@Nullable Context context) {
        player.execute(context, Player.class).setAllowFlight(false);
    }

    @Override
    public void set(@Nullable Context context, @NotNull PsiElement<?> object) {
        player.execute(context, Player.class).setAllowFlight(object.execute(context, Boolean.class));
    }

    /**
     * A factory for creating {@link PsiFlyModeExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiFlyModeExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiFlyModeExpression create(@NotNull PsiElement<?> player, int lineNumber) {
            return new PsiFlyModeExpressionImpl(player, lineNumber);
        }
    }
}
