package com.github.stefvanschie.quickskript.spigot.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsInLoveCondition;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.entity.Animals;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the living entities are in love.
 *
 * @since 0.1.0
 */
public class PsiIsInLoveConditionImpl extends PsiIsInLoveCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param livingEntities the living entities to check if they are in love mode
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsInLoveConditionImpl(@NotNull PsiElement<?> livingEntities, boolean positive, int lineNumber) {
        super(livingEntities, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        MultiResult<? extends LivingEntity> livingEntities = super.livingEntities.executeMulti(
            environment,
            context,
            LivingEntity.class
        );

        return super.positive == livingEntities.test(entity ->
            entity instanceof Animals animals && animals.isLoveMode());
    }

    /**
     * A factory for creating {@link PsiIsInLoveConditionImpl}s.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsInLoveCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        @Override
        public PsiIsInLoveCondition create(@NotNull PsiElement<?> livingEntities, boolean positive, int lineNumber) {
            return new PsiIsInLoveConditionImpl(livingEntities, positive, lineNumber);
        }
    }
}
