package com.github.stefvanschie.quickskript.spigot.psi.expression;

import com.github.stefvanschie.quickskript.spigot.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiHoverListExpression;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.server.ServerListPingEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Gets the hover list. This cannot be pre-computed, since it may change during gameplay.
 *
 * @since 0.1.0
 */
public class PsiHoverListExpressionImpl extends PsiHoverListExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiHoverListExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Collection<String> executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("This expression can only be used inside events", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof ServerListPingEvent pingEvent)) {
            throw new ExecutionException("This expression can only be used inside ping events", lineNumber);
        }

        Collection<String> names = new ArrayList<>();

        for (Player player : pingEvent) {
            names.add(player.getName());
        }

        return names;
    }

    @Override
    public void delete(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("This expression can only be used inside events", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof ServerListPingEvent)) {
            throw new ExecutionException("This expression can only be used inside ping events", lineNumber);
        }

        for (Iterator<Player> iterator = ((ServerListPingEvent) event).iterator(); iterator.hasNext(); ) {
            iterator.remove();
        }
    }

    @Override
    public void remove(@Nullable SkriptRunEnvironment environment, @Nullable Context context, @NotNull PsiElement<?> object) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("This expression can only be used inside events", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof ServerListPingEvent)) {
            throw new ExecutionException("This expression can only be used inside ping events", lineNumber);
        }

        Player player = object.execute(environment, context, Player.class);

        for (Iterator<? extends Player> iterator = ((ServerListPingEvent) event).iterator(); iterator.hasNext(); ) {
            if (iterator.next().equals(player)) {
                iterator.remove();
            }
        }
    }

    @Override
    public void reset(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        delete(environment, context);
    }

    /**
     * A factory for creating {@link PsiHoverListExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiHoverListExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiHoverListExpression create(int lineNumber) {
            return new PsiHoverListExpressionImpl(lineNumber);
        }
    }
}
