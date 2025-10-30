package com.github.stefvanschie.quickskript.spigot.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiCanPickUpItemsCondition;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the specified living entities can pick up items.
 *
 * @since 0.1.0
 */
public class PsiCanPickUpItemsConditionImpl extends PsiCanPickUpItemsCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiCanPickUpItemsConditionImpl(@NotNull PsiElement<?> livingEntities, boolean positive, int lineNumber) {
        super(livingEntities, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        MultiResult<? extends LivingEntity> livingEntities = super.livingEntities.executeMulti(environment, context,
            LivingEntity.class);

        return super.positive == livingEntities.test(LivingEntity::getCanPickupItems);
    }

    /**
     * A factory for creating {@link PsiCanPickUpItemsConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiCanPickUpItemsCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiCanPickUpItemsCondition create(
            @NotNull PsiElement<?> livingEntities,
            boolean positive,
            int lineNumber
        ) {
            return new PsiCanPickUpItemsConditionImpl(livingEntities, positive, lineNumber);
        }
    }
}
