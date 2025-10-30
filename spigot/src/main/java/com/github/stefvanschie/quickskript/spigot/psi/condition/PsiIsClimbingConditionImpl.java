package com.github.stefvanschie.quickskript.spigot.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsClimbingCondition;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the living entities are climbing.
 *
 * @since 0.1.0
 */
public class PsiIsClimbingConditionImpl extends PsiIsClimbingCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param livingEntities the living entities to check if they are climbing
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsClimbingConditionImpl(@NotNull PsiElement<?> livingEntities, boolean positive, int lineNumber) {
        super(livingEntities, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        MultiResult<? extends LivingEntity> livingEntities = super.livingEntities.executeMulti(
            environment,
            context,
            LivingEntity.class
        );

        return super.positive == livingEntities.test(LivingEntity::isClimbing);
    }

    /**
     * A factory for creating {@link PsiIsClimbingConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsClimbingCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiIsClimbingCondition create(@NotNull PsiElement<?> livingEntities, boolean positive, int lineNumber) {
            return new PsiIsClimbingConditionImpl(livingEntities, positive, lineNumber);
        }
    }
}
