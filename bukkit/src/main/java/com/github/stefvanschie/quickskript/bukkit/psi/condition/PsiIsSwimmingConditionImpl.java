package com.github.stefvanschie.quickskript.bukkit.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsSwimmingCondition;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether a living entity is currently swimming. This cannot be pre computed, since living entities may stop or
 * start swimming during game play.
 *
 * @since 0.1.0
 */
public class PsiIsSwimmingConditionImpl extends PsiIsSwimmingCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param livingEntity the living entity to check whether they're swimming
     * @param positive     false if the execution result needs to be inverted
     * @param lineNumber   the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsSwimmingConditionImpl(@NotNull PsiElement<?> livingEntity, boolean positive, int lineNumber) {
        super(livingEntity, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        return positive == livingEntity.execute(context, LivingEntity.class).isSwimming();
    }

    /**
     * A factory for creating {@link PsiIsSwimmingConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsSwimmingCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiIsSwimmingCondition create(@NotNull PsiElement<?> livingEntity, boolean positive, int lineNumber) {
            return new PsiIsSwimmingConditionImpl(livingEntity, positive, lineNumber);
        }
    }
}
