package com.github.stefvanschie.quickskript.paper.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsSaddledCondition;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.Material;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Steerable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether the living entities are saddled.
 *
 * @since 0.1.0
 */
public class PsiIsSaddledConditionImpl extends PsiIsSaddledCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsSaddledConditionImpl(@NotNull PsiElement<?> livingEntities, boolean properly, boolean positive,
                                      int lineNumber) {
        super(livingEntities, properly, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        MultiResult<? extends LivingEntity> livingEntities = super.livingEntities.executeMulti(environment, context,
            LivingEntity.class);

        return super.positive == livingEntities.test(livingEntity -> {
            if (livingEntity instanceof Steerable steerable) {
                return steerable.hasSaddle();
            }

            if (livingEntity instanceof AbstractHorse horse) {
                ItemStack saddle = horse.getInventory().getSaddle();

                return saddle != null && (!super.properly || saddle.getType() == Material.SADDLE);
            }

            return false;
        });
    }

    /**
     * A factory for creating {@link PsiIsSaddledConditionImpl}s.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsSaddledCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _, _ -> new", pure = true)
        @Override
        public PsiIsSaddledCondition create(@NotNull PsiElement<?> livingEntities, boolean properly, boolean positive,
                                            int lineNumber) {
            return new PsiIsSaddledConditionImpl(livingEntities, properly, positive, lineNumber);
        }
    }
}