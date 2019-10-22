package com.github.stefvanschie.quickskript.bukkit.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiCanSeeCondition;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether a player can see another player. This cannot be pre computed, since this may change during game play.
 *
 * @since 0.1.0
 */
public class PsiCanSeeConditionImpl extends PsiCanSeeCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param player the player
     * @param targetPlayer the player to test against
     * @param positive false if the result should be negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiCanSeeConditionImpl(@NotNull PsiElement<?> player, @NotNull PsiElement<?> targetPlayer,
                                     boolean positive, int lineNumber) {
        super(player, targetPlayer, positive, lineNumber);
    }

    @Nullable
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        return positive == player.execute(context, Player.class).canSee(targetPlayer.execute(context, Player.class));
    }

    /**
     * A factory for creating {@link PsiCanSeeConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiCanSeeCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiCanSeeCondition create(@NotNull PsiElement<?> player, @NotNull PsiElement<?> targetPlayer,
                                         boolean positive, int lineNumber) {
            return new PsiCanSeeConditionImpl(player, targetPlayer, positive, lineNumber);
        }
    }
}
