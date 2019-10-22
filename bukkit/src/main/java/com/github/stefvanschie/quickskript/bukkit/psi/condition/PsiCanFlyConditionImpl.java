package com.github.stefvanschie.quickskript.bukkit.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiCanFlyCondition;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the specified player is able to fly. This cannot be pre computed, since this value may change during
 * game play.
 *
 * @since 0.1.0
 */
public class PsiCanFlyConditionImpl extends PsiCanFlyCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param player     the player to test for
     * @param positive   whether the result needs to be inverted or not, see {@link #positive}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiCanFlyConditionImpl(PsiElement<?> player, boolean positive, int lineNumber) {
        super(player, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        return positive == player.execute(context, Player.class).getAllowFlight();
    }

    /**
     * A factory for creating {@link PsiCanFlyConditionImpl}s.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiCanFlyCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        protected PsiCanFlyCondition create(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
            return new PsiCanFlyConditionImpl(player, positive, lineNumber);
        }
    }
}
