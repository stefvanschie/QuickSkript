package com.github.stefvanschie.quickskript.bukkit.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiHasClientWeatherCondition;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether the player has a custom type of weather. This cannot be pre computed, since this may change during
 * game play.
 *
 * @since 0.1.0
 */
public class PsiHasClientWeatherConditionImpl extends PsiHasClientWeatherCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to check for
     * @param positive false if the result should be negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiHasClientWeatherConditionImpl(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
        super(player, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        return positive == (player.execute(context, Player.class).getPlayerWeather() != null);
    }

    /**
     * A factory for creating {@link PsiHasClientWeatherConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiHasClientWeatherCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiHasClientWeatherCondition create(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
            return new PsiHasClientWeatherConditionImpl(player, positive, lineNumber);
        }
    }
}
