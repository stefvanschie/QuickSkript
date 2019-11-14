package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiDefaultServerIconExpression;
import org.bukkit.Bukkit;
import org.bukkit.util.CachedServerIcon;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the default server icon
 *
 * @since 0.1.0
 */
public class PsiDefaultServerIconExpressionImpl extends PsiDefaultServerIconExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiDefaultServerIconExpressionImpl(int lineNumber) {
        super(lineNumber);

        preComputed = Bukkit.getServerIcon();
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected CachedServerIcon executeImpl(@Nullable Context context) {
        throw new ExecutionException("This expression is always pre-computed", lineNumber);
    }

    /**
     * A factory for creating {@link PsiDefaultServerIconExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiDefaultServerIconExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiDefaultServerIconExpression create(int lineNumber) {
            return new PsiDefaultServerIconExpressionImpl(lineNumber);
        }
    }
}
