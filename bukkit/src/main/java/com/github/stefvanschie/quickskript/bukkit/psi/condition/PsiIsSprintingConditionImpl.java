package com.github.stefvanschie.quickskript.bukkit.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsSprintingCondition;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether a player is currently sprinting. This cannot be pre computed, since the player may stop or start
 * sprinting during game play.
 *
 * @since 0.1.0
 */
public class PsiIsSprintingConditionImpl extends PsiIsSprintingCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param player     the player to test their sprinting state
     * @param positive   false if the result of execution needs to be inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsSprintingConditionImpl(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
        super(player, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        return positive == player.execute(context, Player.class).isSprinting();
    }

    /**
     * A factory for creating {@link PsiIsSprintingConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsSprintingCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiIsSprintingCondition create(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
            return new PsiIsSprintingConditionImpl(player, positive, lineNumber);
        }
    }
}
