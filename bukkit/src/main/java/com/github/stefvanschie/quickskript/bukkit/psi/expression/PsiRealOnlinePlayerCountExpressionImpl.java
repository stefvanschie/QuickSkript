package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiRealOnlinePlayerCountExpression;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the real amount of online players
 *
 * @since 0.1.0
 */
public class PsiRealOnlinePlayerCountExpressionImpl extends PsiRealOnlinePlayerCountExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiRealOnlinePlayerCountExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Integer executeImpl(@Nullable Context context) {
        return Bukkit.getOnlinePlayers().size();
    }

    /**
     * A factory for creating {@link PsiRealOnlinePlayerCountExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiRealOnlinePlayerCountExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiRealOnlinePlayerCountExpression create(int lineNumber) {
            return new PsiRealOnlinePlayerCountExpressionImpl(lineNumber);
        }
    }
}
