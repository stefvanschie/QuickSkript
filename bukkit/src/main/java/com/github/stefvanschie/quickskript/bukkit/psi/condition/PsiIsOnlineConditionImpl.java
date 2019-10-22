package com.github.stefvanschie.quickskript.bukkit.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsOnlineCondition;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether a player is online. This cannot be pre computed, since players can log in/log out during game play.
 *
 * @since 0.1.0
 */
public class PsiIsOnlineConditionImpl extends PsiIsOnlineCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param offlinePlayer the offline player to check whether they are online
     * @param positive      false if the result of the execution should be negated
     * @param lineNumber    the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsOnlineConditionImpl(@NotNull PsiElement<?> offlinePlayer, boolean positive, int lineNumber) {
        super(offlinePlayer, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        return positive == offlinePlayer.execute(context, OfflinePlayer.class).isOnline();
    }

    /**
     * A factory for creating {@link PsiIsOnlineConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsOnlineCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiIsOnlineCondition create(@NotNull PsiElement<?> offlinePlayer, boolean positive, int lineNumber) {
            return new PsiIsOnlineConditionImpl(offlinePlayer, positive, lineNumber);
        }
    }
}
