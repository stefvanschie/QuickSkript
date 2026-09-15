package com.github.stefvanschie.quickskript.spigot.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsRiptidingCondition;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the provided living entities are riptiding.
 *
 * @since 0.1.0
 */
public class PsiIsRiptidingConditionImpl extends PsiIsRiptidingCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param livingEntities the living entities to check if they are riptiding
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsRiptidingConditionImpl(@NotNull PsiElement<?> livingEntities, boolean positive, int lineNumber) {
        super(livingEntities, positive, lineNumber);
    }

    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return super.positive == super.livingEntities.executeMulti(environment, context, LivingEntity.class)
            .test(LivingEntity::isRiptiding);
    }

    /**
     * A factory for creating instances of {@link PsiIsRiptidingCondition}.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsRiptidingCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        @Override
        public PsiIsRiptidingCondition create(@NotNull PsiElement<?> livingEntities, boolean positive, int lineNumber) {
            return new PsiIsRiptidingConditionImpl(livingEntities, positive, lineNumber);
        }
    }
}
