package com.github.stefvanschie.quickskript.bukkit.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsBurningCondition;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether an entity is burning. This cannot be pre computed, since an entity can get burned and/or extinguished
 * during game play.
 *
 * @since 0.1.0
 */
public class PsiIsBurningConditionImpl extends PsiIsBurningCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param entity     the entity to check whether they are burning
     * @param positive   false if the execution result needs to be inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsBurningConditionImpl(@NotNull PsiElement<?> entity, boolean positive, int lineNumber) {
        super(entity, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        return positive == (entity.execute(context, Entity.class).getFireTicks() > 0);
    }

    /**
     * A factory for creating {@link PsiIsBurningConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsBurningCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiIsBurningCondition create(@NotNull PsiElement<?> entity, boolean positive, int lineNumber) {
            return new PsiIsBurningConditionImpl(entity, positive, lineNumber);
        }
    }
}
