package com.github.stefvanschie.quickskript.paper.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsPlayingDeadCondition;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the living entities are playing dead.
 *
 * @since 0.1.0
 */
public class PsiIsPlayingDeadConditionImpl extends PsiIsPlayingDeadCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param livingEntities the living entities to check if they are playing dead
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsPlayingDeadConditionImpl(@NotNull PsiElement<?> livingEntities, boolean positive, int lineNumber) {
        super(livingEntities, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return super.positive == super.livingEntities.executeMulti(environment, context, LivingEntity.class)
            .test(livingEntity -> livingEntity instanceof Axolotl axolotl && axolotl.isPlayingDead());
    }

    /**
     * A factory for creating instances of {@link PsiIsPlayingDeadConditionImpl}.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsPlayingDeadCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        @Override
        public PsiIsPlayingDeadCondition create(@NotNull PsiElement<?> livingEntities, boolean positive,
                                                int lineNumber) {
            return new PsiIsPlayingDeadConditionImpl(livingEntities, positive, lineNumber);
        }
    }
}
