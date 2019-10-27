package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.bukkit.context.ExecuteContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.context.ExecuteContext;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiMeExpression;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets who ran the execute command.
 *
 * @see ExecuteContext
 * @since 0.1.0
 */
public class PsiMeExpressionImpl extends PsiMeExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiMeExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    @Nullable
    @Contract(pure = true)
    @Override
    protected CommandSender executeImpl(@Nullable Context context) {
        if (!(context instanceof ExecuteContext))
            throw new ExecutionException("This expression can only be used inside execute commands", lineNumber);

        return ((ExecuteContextImpl) context).getCommandSender();
    }

    /**
     * A factory for creating {@link PsiMeExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiMeExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiMeExpressionImpl create(int lineNumber) {
            return new PsiMeExpressionImpl(lineNumber);
        }
    }
}
