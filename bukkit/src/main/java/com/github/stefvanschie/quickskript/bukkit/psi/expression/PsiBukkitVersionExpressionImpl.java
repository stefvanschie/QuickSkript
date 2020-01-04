package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiBukkitVersionExpression;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the Bukkit version. This is always pre-computed.
 *
 * @since 0.1.0
 */
public class PsiBukkitVersionExpressionImpl extends PsiBukkitVersionExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiBukkitVersionExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Text executeImpl(@Nullable Context context) {
        return Text.parseLiteral(Bukkit.getBukkitVersion());
    }

    /**
     * A factory for creating {@link PsiBukkitVersionExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiBukkitVersionExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiBukkitVersionExpression create(int lineNumber) {
            return new PsiBukkitVersionExpressionImpl(lineNumber);
        }
    }
}
