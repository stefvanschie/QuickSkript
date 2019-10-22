package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.bukkit.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiCommandExpression;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the command used in the event, either the full command, the base command or the arguments
 *
 * @since 0.1.0
 */
public class PsiCommandExpressionImpl extends PsiCommandExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param part the part of the command we want to get, see {@link #part}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiCommandExpressionImpl(@NotNull Part part, int lineNumber) {
        super(part, lineNumber);
    }

    @NotNull
    @Override
    protected String executeImpl(@Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Command expression can only be used inside events", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        String full;

        if (event instanceof PlayerCommandPreprocessEvent) {
            full = ((PlayerCommandPreprocessEvent) event).getMessage().substring(1);
        } else if (event instanceof ServerCommandEvent) {
            full = ((ServerCommandEvent) event).getCommand();
        } else {
            throw new ExecutionException("Command expression can only be used inside command events", lineNumber);
        }

        if (part == Part.FULL) {
            return full;
        }

        int spaceIndex = full.indexOf(' ');

        if (part == Part.LABEL) {
            return full.substring(0, spaceIndex);
        }

        if (part == Part.ARGUMENTS) {
            return full.substring(spaceIndex + 1);
        }

        throw new ExecutionException("Unknown part type for command expression", lineNumber);
    }

    /**
     * A factory for creating {@link PsiCommandExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiCommandExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiCommandExpression create(@NotNull Part part, int lineNumber) {
            return new PsiCommandExpressionImpl(part, lineNumber);
        }
    }
}
