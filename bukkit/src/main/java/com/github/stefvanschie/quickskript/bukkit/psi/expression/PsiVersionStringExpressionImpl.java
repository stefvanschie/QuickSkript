package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import com.github.stefvanschie.quickskript.bukkit.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiVersionStringExpression;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the version shown when the server is pinged.
 *
 * @since 0.1.0
 */
public class PsiVersionStringExpressionImpl extends PsiVersionStringExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiVersionStringExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Text executeImpl(@Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Version string expression can only be executed inside an event", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof PaperServerListPingEvent)) {
            throw new ExecutionException("Version string expression can only be executed inside a ping event",
                lineNumber);
        }

        return Text.parseLiteral(((PaperServerListPingEvent) event).getVersion());
    }

    @Override
    public void set(@Nullable Context context, @NotNull PsiElement<?> object) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Version string expression can only be executed inside an event", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof PaperServerListPingEvent)) {
            throw new ExecutionException("Version string expression can only be executed inside a ping event",
                lineNumber);
        }

        ((PaperServerListPingEvent) event).setVersion(object.execute(context, Text.class).toString());
    }

    /**
     * A factory for creating {@link PsiVersionStringExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiVersionStringExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiVersionStringExpression create(int lineNumber) {
            return new PsiVersionStringExpressionImpl(lineNumber);
        }
    }
}
