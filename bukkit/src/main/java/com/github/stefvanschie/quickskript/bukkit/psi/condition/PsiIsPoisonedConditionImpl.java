package com.github.stefvanschie.quickskript.bukkit.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsPoisonedCondition;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks to see if a living entity is poisoned. This cannot be pre computed, since entities may get poisoned or be
 * healed of poison during game play.
 *
 * @since 0.1.0
 */
public class PsiIsPoisonedConditionImpl extends PsiIsPoisonedCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param livingEntity the living entity to check whether they are poisoned
     * @param positive     if false, the condition needs to be inverted
     * @param lineNumber   the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsPoisonedConditionImpl(@NotNull PsiElement<?> livingEntity, boolean positive, int lineNumber) {
        super(livingEntity, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        return positive == livingEntity.execute(context, LivingEntity.class).hasPotionEffect(PotionEffectType.POISON);
    }

    /**
     * A factory for creating {@link PsiIsPoisonedConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsPoisonedCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiIsPoisonedCondition create(@NotNull PsiElement<?> livingEntity, boolean positive, int lineNumber) {
            return new PsiIsPoisonedConditionImpl(livingEntity, positive, lineNumber);
        }
    }
}
