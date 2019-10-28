package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiOfflinePlayersExpression;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets an array of all players that have ever joined this server. This includes both players that are currently online
 * and players that are currently offline.
 *
 * @since 0.1.0
 */
public class PsiOfflinePlayersExpressionImpl extends PsiOfflinePlayersExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiOfflinePlayersExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected OfflinePlayer[] executeImpl(@Nullable Context context) {
        return Bukkit.getOfflinePlayers();
    }

    /**
     * A factory for creating {@link PsiOfflinePlayersExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiOfflinePlayersExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiOfflinePlayersExpression create(int lineNumber) {
            return new PsiOfflinePlayersExpressionImpl(lineNumber);
        }
    }
}
