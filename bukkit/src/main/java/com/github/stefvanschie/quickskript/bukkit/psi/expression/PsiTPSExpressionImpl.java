package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiTPSExpression;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the TPS over a certain period of time
 *
 * @since 0.1.0
 */
public class PsiTPSExpressionImpl extends PsiTPSExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param timespan the timespan for which to get the tps
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiTPSExpressionImpl(@Nullable Timespan timespan, int lineNumber) {
        super(timespan, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected double[] executeImpl(@Nullable Context context) {
        if (timespan == null) {
            return Bukkit.getTPS();
        }

        if (timespan == Timespan.ONE_MINUTE) {
            return new double[] {Bukkit.getTPS()[0]};
        }

        if (timespan == Timespan.FIVE_MINUTES) {
            return new double[] {Bukkit.getTPS()[1]};
        }

        if (timespan == Timespan.FIFTEEN_MINUTES) {
            return new double[] {Bukkit.getTPS()[2]};
        }

        throw new ExecutionException(new UnsupportedOperationException("Unknown timespan value"), lineNumber);
    }

    /**
     * A factory for creating {@link PsiTPSExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiTPSExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiTPSExpression create(@Nullable Timespan timespan, int lineNumber) {
            return new PsiTPSExpressionImpl(timespan, lineNumber);
        }
    }
}
