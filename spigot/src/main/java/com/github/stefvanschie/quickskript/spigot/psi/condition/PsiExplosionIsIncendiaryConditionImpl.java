package com.github.stefvanschie.quickskript.spigot.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiExplosionIsIncendiaryCondition;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.spigot.context.EventContextImpl;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if an explosion is incendiary.
 *
 * @since 0.1.0
 */
public class PsiExplosionIsIncendiaryConditionImpl extends PsiExplosionIsIncendiaryCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiExplosionIsIncendiaryConditionImpl(boolean positive, int lineNumber) {
        super(positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        if (!(context instanceof EventContextImpl eventContext) ||
            !(eventContext.getEvent() instanceof ExplosionPrimeEvent event)) {
            throw new ExecutionException("Unable to check if an explosion is incendiary outside an explosion event",
                super.lineNumber);
        }

        return super.positive == event.getFire();
    }

    /**
     * A factory for creating {@link PsiExplosionIsIncendiaryConditionImpl}s.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiExplosionIsIncendiaryCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiExplosionIsIncendiaryCondition create(boolean positive, int lineNumber) {
            return new PsiExplosionIsIncendiaryConditionImpl(positive, lineNumber);
        }
    }
}
