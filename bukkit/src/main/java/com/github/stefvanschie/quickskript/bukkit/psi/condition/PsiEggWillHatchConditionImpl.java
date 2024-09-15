package com.github.stefvanschie.quickskript.bukkit.psi.condition;

import com.github.stefvanschie.quickskript.bukkit.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiEggWillHatchCondition;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether the egg in a player egg throw event will hatch.
 *
 * @since 0.1.0
 */
public class PsiEggWillHatchConditionImpl extends PsiEggWillHatchCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param positive if false, the result is inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiEggWillHatchConditionImpl(boolean positive, int lineNumber) {
        super(positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        if (!(context instanceof EventContextImpl eventContext) ||
            !(eventContext.getEvent() instanceof PlayerEggThrowEvent event)) {
            throw new ExecutionException(
                "Can only check if eggs will hatch from a player throw egg event", super.lineNumber
            );
        }

        return super.positive == event.isHatching();
    }

    /**
     * A factory for creating {@link PsiEggWillHatchConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiEggWillHatchCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiEggWillHatchCondition create(boolean positive, int lineNumber) {
            return new PsiEggWillHatchConditionImpl(positive, lineNumber);
        }
    }
}
