package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.bukkit.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiDisplayedMOTDExpression;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.server.ServerListPingEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The MOTD that is being displayed. This cannot be pre-computed, since this may change during gameplay.
 *
 * @since 0.1.0
 */
public class PsiDisplayedMOTDExpressionImpl extends PsiDisplayedMOTDExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiDisplayedMOTDExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Text executeImpl(@Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Expression can only be executed from an event", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof ServerListPingEvent)) {
            throw new ExecutionException("Expression can only be executed from a ping event", lineNumber);
        }

        return Text.parse(((ServerListPingEvent) event).getMotd());
    }

    @Override
    public void delete(@Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Expression can only be executed from an event", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof ServerListPingEvent)) {
            throw new ExecutionException("Expression can only be executed from a ping event", lineNumber);
        }

        ((ServerListPingEvent) event).setMotd("");
    }

    @Override
    public void reset(@Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Expression can only be executed from an event", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof ServerListPingEvent)) {
            throw new ExecutionException("Expression can only be executed from a ping event", lineNumber);
        }

        ((ServerListPingEvent) event).setMotd(Bukkit.getMotd());
    }

    @Override
    public void set(@Nullable Context context, @NotNull PsiElement<?> object) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Expression can only be executed from an event", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof ServerListPingEvent)) {
            throw new ExecutionException("Expression can only be executed from a ping event", lineNumber);
        }

        ((ServerListPingEvent) event).setMotd(object.execute(context, Text.class).toString());
    }

    /**
     * A factory for creating {@link PsiDisplayedMOTDExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiDisplayedMOTDExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiDisplayedMOTDExpression create(int lineNumber) {
            return new PsiDisplayedMOTDExpressionImpl(lineNumber);
        }
    }
}
