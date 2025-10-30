package com.github.stefvanschie.quickskript.paper.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiEntityIsShearedCondition;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Shearable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Check whether entities are sheared.
 *
 * @since 0.1.0
 */
public class PsiEntityIsShearedConditionImpl extends PsiEntityIsShearedCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param entities the entities to check
     * @param positive if false, the result is inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiEntityIsShearedConditionImpl(@NotNull PsiElement<?> entities, boolean positive, int lineNumber) {
        super(entities, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return super.positive == super.entities.executeMulti(environment, context, LivingEntity.class).test(entity -> {
           if (!(entity instanceof Shearable shearable)) {
               return false;
           }

           return shearable.isSheared();
        });
    }

    /**
     * A factory for creating {@link PsiEntityIsShearedConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiEntityIsShearedCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiEntityIsShearedCondition create(@NotNull PsiElement<?> entities, boolean positive, int lineNumber) {
            return new PsiEntityIsShearedConditionImpl(entities, positive, lineNumber);
        }
    }
}
