package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiLoadServerIconEffect;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiLoadedServerIconExpression;
import com.github.stefvanschie.quickskript.core.util.TemporaryCache;
import org.bukkit.util.CachedServerIcon;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the last loaded server icon, that was loaded via {@link PsiLoadServerIconEffect}.
 *
 * @since 0.1.0
 */
public class PsiLoadedServerIconExpressionImpl extends PsiLoadedServerIconExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiLoadedServerIconExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    @Nullable
    @Contract(pure = true)
    @Override
    protected CachedServerIcon executeImpl(@Nullable Context context) {
        return TemporaryCache.get("last-server-icon");
    }

    /**
     * A factory for creating {@link PsiLoadedServerIconExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiLoadedServerIconExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiLoadedServerIconExpression create(int lineNumber) {
            return new PsiLoadedServerIconExpressionImpl(lineNumber);
        }
    }
}
