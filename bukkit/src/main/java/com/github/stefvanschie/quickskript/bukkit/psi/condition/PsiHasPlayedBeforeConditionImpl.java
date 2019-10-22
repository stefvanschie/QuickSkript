package com.github.stefvanschie.quickskript.bukkit.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiHasPlayedBeforeCondition;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether a player has played before. This cannot be pre computed, since players can log out and log in,
 * changing the result of this element.
 *
 * @since 0.1.0
 */
public class PsiHasPlayedBeforeConditionImpl extends PsiHasPlayedBeforeCondition {

    private PsiHasPlayedBeforeConditionImpl(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
        super(player, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        return positive == player.execute(context, OfflinePlayer.class).hasPlayedBefore();
    }

    /**
     * A factory for creating psi has permission conditions
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiHasPlayedBeforeCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiHasPlayedBeforeCondition create(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
            return new PsiHasPlayedBeforeConditionImpl(player, positive, lineNumber);
        }
    }
}
