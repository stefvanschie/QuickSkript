package com.github.stefvanschie.quickskript.paper.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiFishingHookExpression;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.paper.context.EventContextImpl;
import org.bukkit.entity.FishHook;
import org.bukkit.event.player.PlayerFishEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the fishing hook from a fishing event.
 *
 * @since 0.1.0
 */
public class PsiFishingHookExpressionImpl extends PsiFishingHookExpression {

    /**
     * Creates a new element with the given line number.
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiFishingHookExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    @NotNull
    @Contract("_, null -> fail")
    @Override
    protected FishHook executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        if (!(context instanceof EventContextImpl eventContext) ||
            !(eventContext.getEvent() instanceof PlayerFishEvent event)) {
            throw new ExecutionException("Can only get fishing hook from fishing event", super.lineNumber);
        }

        return event.getHook();
    }

    /**
     * A factory for creating {@link PsiFishingHookExpressionImpl}s.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiFishingHookExpression.Factory {

        @NotNull
        @Contract(value = "_ -> new", pure = true)
        @Override
        public PsiFishingHookExpression create(int lineNumber) {
            return new PsiFishingHookExpressionImpl(lineNumber);
        }
    }
}
