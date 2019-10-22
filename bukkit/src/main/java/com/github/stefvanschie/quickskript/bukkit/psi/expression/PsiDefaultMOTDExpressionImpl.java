package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiDefaultMOTDExpression;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the default message of the day
 *
 * @since 0.1.0
 */
public class PsiDefaultMOTDExpressionImpl extends PsiDefaultMOTDExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiDefaultMOTDExpressionImpl(int lineNumber) {
        super(lineNumber);

        preComputed = Text.parse(Bukkit.getMotd());
    }

    /**
     * @throws ExecutionException this expression is always pre-computed
     */
    @NotNull
    @Override
    protected Text executeImpl(@Nullable Context context) {
        throw new ExecutionException(new IllegalStateException("Default MOTD is always pre-computed"), lineNumber);
    }

    /**
     * A factory for creating {@link PsiDefaultMOTDExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiDefaultMOTDExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiDefaultMOTDExpression create(int lineNumber) {
            return new PsiDefaultMOTDExpressionImpl(lineNumber);
        }
    }
}
