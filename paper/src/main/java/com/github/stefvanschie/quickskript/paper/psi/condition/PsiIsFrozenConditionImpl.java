package com.github.stefvanschie.quickskript.paper.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsFrozenCondition;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether the entities are frozen.
 *
 * @since 0.1.0
 */
public class PsiIsFrozenConditionImpl extends PsiIsFrozenCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param entities the entities to check if they are frozen
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsFrozenConditionImpl(@NotNull PsiElement<?> entities, boolean positive, int lineNumber) {
        super(entities, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return super.positive == super.entities.executeMulti(environment, context, Entity.class).test(Entity::isFrozen);
    }

    /**
     * A factory for creating {@link PsiIsFrozenConditionImpl}s.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsFrozenCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        @Override
        public PsiIsFrozenCondition create(@NotNull PsiElement<?> entities, boolean positive, int lineNumber) {
            return new PsiIsFrozenConditionImpl(entities, positive, lineNumber);
        }
    }
}
