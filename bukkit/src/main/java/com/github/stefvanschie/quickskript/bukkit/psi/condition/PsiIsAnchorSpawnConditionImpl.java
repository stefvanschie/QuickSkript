package com.github.stefvanschie.quickskript.bukkit.psi.condition;

import com.github.stefvanschie.quickskript.bukkit.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsAnchorSpawnCondition;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the respawn location in a respawn event is a respawn anchor.
 *
 * @since 0.1.0
 */
public class PsiIsAnchorSpawnConditionImpl extends PsiIsAnchorSpawnCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsAnchorSpawnConditionImpl(boolean positive, int lineNumber) {
        super(positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        if (!(context instanceof EventContextImpl eventContext)
            || !(eventContext.getEvent() instanceof PlayerRespawnEvent event)) {
            throw new ExecutionException(
                "Is anchor spawn condition can only be executed in a respawn event",
                super.lineNumber
            );
        }

        return super.positive == event.isAnchorSpawn();
    }

    /**
     * A factory for creating {@link PsiIsAnchorSpawnConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsAnchorSpawnCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiIsAnchorSpawnCondition create(boolean positive, int lineNumber) {
            return new PsiIsAnchorSpawnConditionImpl(positive, lineNumber);
        }
    }
}
