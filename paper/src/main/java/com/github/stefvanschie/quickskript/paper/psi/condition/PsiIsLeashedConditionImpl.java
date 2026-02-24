package com.github.stefvanschie.quickskript.paper.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsLeashedCondition;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the living entities are leashed.
 *
 * @since 0.1.0
 */
public class PsiIsLeashedConditionImpl extends PsiIsLeashedCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param livingEntities the living entities to check if they are leashed
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsLeashedConditionImpl(@NotNull PsiElement<?> livingEntities, boolean positive, int lineNumber) {
        super(livingEntities, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return super.positive == super.livingEntities.executeMulti(environment, context, LivingEntity.class)
            .test(LivingEntity::isLeashed);
    }

    /**
     * A factory for creating {@link PsiIsLeashedConditionImpl}s.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsLeashedCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        @Override
        public PsiIsLeashedCondition create(@NotNull PsiElement<?> livingEntities, boolean positive, int lineNumber) {
            return new PsiIsLeashedConditionImpl(livingEntities, positive, lineNumber);
        }
    }
}
