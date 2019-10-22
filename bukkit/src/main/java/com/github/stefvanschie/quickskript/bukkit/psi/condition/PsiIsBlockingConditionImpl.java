package com.github.stefvanschie.quickskript.bukkit.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsBlockingCondition;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether a player is blocking. This cannot be pre computed, since a player can block and/or unblock during
 * game play.
 *
 * @since 0.1.0
 */
public class PsiIsBlockingConditionImpl extends PsiIsBlockingCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param player     the player to check whether they are blocking
     * @param positive   false if the execution result needs to be inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsBlockingConditionImpl(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
        super(player, positive, lineNumber);
    }

    @Nullable
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        return positive == player.execute(context, Player.class).isBlocking();
    }

    /**
     * A factory for creating {@link PsiIsBlockingConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsBlockingCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiIsBlockingCondition create(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
            return new PsiIsBlockingConditionImpl(player, positive, lineNumber);
        }
    }
}
