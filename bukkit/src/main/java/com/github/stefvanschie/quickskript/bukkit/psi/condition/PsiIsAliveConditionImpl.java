package com.github.stefvanschie.quickskript.bukkit.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsAliveCondition;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether a living entity is alive or not. This cannot be pre computed, since the living entity may be
 * killed/revived during game play.
 *
 * @since 0.1.0
 */
public class PsiIsAliveConditionImpl extends PsiIsAliveCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param livingEntity the living entity to check the alive state for, see {@link #livingEntity}
     * @param positive false if the result of execution needs to be inverted
     * @param lineNumber   the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsAliveConditionImpl(@NotNull PsiElement<?> livingEntity, boolean positive, int lineNumber) {
        super(livingEntity, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        return positive == !livingEntity.execute(context, LivingEntity.class).isDead();
    }

    /**
     * A factory for creating {@link PsiIsAliveConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsAliveCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiIsAliveCondition create(@NotNull PsiElement<?> livingEntity, boolean positive, int lineNumber) {
            return new PsiIsAliveConditionImpl(livingEntity, positive, lineNumber);
        }
    }
}
