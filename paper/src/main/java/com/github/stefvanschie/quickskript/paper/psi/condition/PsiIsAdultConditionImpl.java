package com.github.stefvanschie.quickskript.paper.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsAdultCondition;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.entity.Ageable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if living entities are adults.
 *
 * @since 0.1.0
 */
public class PsiIsAdultConditionImpl extends PsiIsAdultCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param livingEntities the living entities to check if they are adults
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsAdultConditionImpl(@NotNull PsiElement<?> livingEntities, boolean positive, int lineNumber) {
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

        return super.positive == livingEntities.test(Ageable::isAdult);
    }

    /**
     * A factory for creating {@link PsiIsAdultConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsAdultCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiIsAdultCondition create(@NotNull PsiElement<?> livingEntities, boolean positive, int lineNumber) {
            return new PsiIsAdultConditionImpl(livingEntities, positive, lineNumber);
        }
    }
}
