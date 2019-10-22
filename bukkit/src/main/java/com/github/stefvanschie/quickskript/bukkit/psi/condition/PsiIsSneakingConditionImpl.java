package com.github.stefvanschie.quickskript.bukkit.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsSneakingCondition;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether a player is sneaking. This cannot be pre computed, since players may start or end sneaking during game
 * play.
 *
 * @since 0.1.0
 */
public class PsiIsSneakingConditionImpl extends PsiIsSneakingCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param player     the player to check whether they are sneaking
     * @param positive   if false, the execution result will be inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsSneakingConditionImpl(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
        super(player, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        return positive == player.execute(context, Player.class).isSneaking();
    }

    /**
     * A factory for creating {@link PsiIsSneakingCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsSneakingCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiIsSneakingCondition create(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
            return new PsiIsSneakingConditionImpl(player, positive, lineNumber);
        }
    }
}
