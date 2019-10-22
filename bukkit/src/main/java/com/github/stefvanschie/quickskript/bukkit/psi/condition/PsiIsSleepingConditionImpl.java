package com.github.stefvanschie.quickskript.bukkit.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsSleepingCondition;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether a player is currently sleeping. This cannot be pre computed, since the player may enter or leave the
 * bed during game play.
 *
 * @since 0.1.0
 */
public class PsiIsSleepingConditionImpl extends PsiIsSleepingCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param player     the player to check whether they are sleeping
     * @param positive   if false, the result of this execution will be negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsSleepingConditionImpl(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
        super(player, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        return positive == player.execute(context, Player.class).isSleeping();
    }

    /**
     * A factory for creating {@link PsiIsSleepingConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsSleepingCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiIsSleepingCondition create(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
            return new PsiIsSleepingConditionImpl(player, positive, lineNumber);
        }
    }
}
