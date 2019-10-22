package com.github.stefvanschie.quickskript.bukkit.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsFlyingCondition;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks to see if a player is flying. This cannot be pre computed, since a player's flying state may change during
 * game play.
 *
 * @since 0.1.0
 */
public class PsiIsFlyingConditionImpl extends PsiIsFlyingCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param player     the player to check for
     * @param positive   if false, the result of execution is inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsFlyingConditionImpl(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
        super(player, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        return positive == player.execute(context, Player.class).isFlying();
    }

    /**
     * A factory for creating {@link PsiIsFlyingConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsFlyingCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiIsFlyingCondition create(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
            return new PsiIsFlyingConditionImpl(player, positive, lineNumber);
        }
    }
}
