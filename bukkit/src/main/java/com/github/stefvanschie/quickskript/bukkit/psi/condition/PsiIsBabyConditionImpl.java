package com.github.stefvanschie.quickskript.bukkit.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsBabyCondition;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.entity.Ageable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if living entities are babies.
 *
 * @since 0.1.0
 */
public class PsiIsBabyConditionImpl extends PsiIsBabyCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param livingEntities the living entities to check if they are babies
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsBabyConditionImpl(@NotNull PsiElement<?> livingEntities, boolean positive, int lineNumber) {
        super(livingEntities, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        MultiResult<? extends Ageable> livingEntities = super.livingEntities.executeMulti(
            environment,
            context,
            Ageable.class
        );

        return super.positive == livingEntities.test(livingEntity -> !livingEntity.isAdult());
    }

    /**
     * A factory for creating {@link PsiIsBabyConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsBabyCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiIsBabyCondition create(@NotNull PsiElement<?> livingEntities, boolean positive, int lineNumber) {
            return new PsiIsBabyConditionImpl(livingEntities, positive, lineNumber);
        }
    }
}
