package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiRealMaxPlayersExpression;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the real amount of maximum players that can play on this server at once. This cannot be pre-computed, since this
 * may change during gameplay.
 *
 * @since 0.1.0
 */
public class PsiRealMaxPlayersExpressionImpl extends PsiRealMaxPlayersExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiRealMaxPlayersExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    @Override
    @Contract(pure = true)
    @NotNull
    protected Integer executeImpl(@Nullable Context context) {
        return Bukkit.getMaxPlayers();
    }

    /**
     * A factory for creating {@link PsiRealMaxPlayersExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiRealMaxPlayersExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiRealMaxPlayersExpression create(int lineNumber) {
            return new PsiRealMaxPlayersExpressionImpl(lineNumber);
        }
    }
}
