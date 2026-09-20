package com.github.stefvanschie.quickskript.paper.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsScreamingCondition;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Goat;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether the living entities are screaming.
 *
 * @since 0.1.0
 */
public class PsiIsScreamingConditionImpl extends PsiIsScreamingCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param livingEntities the living entities to check if they are screaming
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsScreamingConditionImpl(@NotNull PsiElement<?> livingEntities, boolean positive, int lineNumber) {
        super(livingEntities, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        MultiResult<? extends LivingEntity> livingEntities = super.livingEntities.executeMulti(environment, context,
            LivingEntity.class);

        return super.positive == livingEntities.test(livingEntity -> {
            if (livingEntity instanceof Enderman enderman) {
                return enderman.isScreaming();
            }

            if (livingEntity instanceof Goat goat) {
                return goat.isScreaming();
            }

            return false;
        });
    }

    /**
     * A factory for creating {@link PsiIsScreamingConditionImpl}s.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsScreamingCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        @Override
        public PsiIsScreamingCondition create(@NotNull PsiElement<?> livingEntities, boolean positive, int lineNumber) {
            return new PsiIsScreamingConditionImpl(livingEntities, positive, lineNumber);
        }
    }
}