package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.core.psi.expression.PsiConsoleSenderExpression;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

/**
 * A type that returns the global command sender object. This element is always pre computed.
 *
 * @since 0.1.0
 */
public class PsiConsoleSenderExpressionImpl extends PsiConsoleSenderExpression {

    /**
     * Creates a new psi console sender type
     *
     * @since 0.1.0
     */
    private PsiConsoleSenderExpressionImpl(int lineNumber) {
        super(lineNumber);

        preComputed = Bukkit.getConsoleSender();
    }

    /**
     * A factory for creating console sender types
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiConsoleSenderExpression.Factory {

        @NotNull
        @Override
        public PsiConsoleSenderExpressionImpl create(int lineNumber) {
            return new PsiConsoleSenderExpressionImpl(lineNumber);
        }
    }
}
