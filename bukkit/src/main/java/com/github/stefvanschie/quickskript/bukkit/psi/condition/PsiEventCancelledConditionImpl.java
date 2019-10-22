package com.github.stefvanschie.quickskript.bukkit.psi.condition;

import com.github.stefvanschie.quickskript.bukkit.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiEventCancelledCondition;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A condition to check whether or not the event has been cancelled. This cannot be pre computed, since it is dependent
 * on {@link Context}.
 *
 * @since 0.1.0
 */
public class PsiEventCancelledConditionImpl extends PsiEventCancelledCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param positive   if false, the result of execution will be inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiEventCancelledConditionImpl(boolean positive, int lineNumber) {
        super(positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Can't check whether the event was cancelled, since there is no event",
                lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        return event instanceof Cancellable && positive == ((Cancellable) event).isCancelled();
    }

    /**
     * A factory for creating {@link PsiEventCancelledConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiEventCancelledCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiEventCancelledCondition create(boolean positive, int lineNumber) {
            return new PsiEventCancelledConditionImpl(positive, lineNumber);
        }
    }
}
