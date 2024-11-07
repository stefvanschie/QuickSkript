package com.github.stefvanschie.quickskript.bukkit.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiEntityIsInLavaCondition;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the entity is in lava.
 *
 * @since 0.1.0
 */
public class PsiEntityIsInLavaConditionImpl extends PsiEntityIsInLavaCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param entities the entities to check if they are in lava
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiEntityIsInLavaConditionImpl(@NotNull PsiElement<?> entities, boolean positive, int lineNumber) {
        super(entities, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return super.positive == super.entities.executeMulti(environment, context, Entity.class).test(Entity::isInLava);
    }

    /**
     * A factory for creating {@link PsiEntityIsInLavaConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiEntityIsInLavaCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiEntityIsInLavaCondition create(@NotNull PsiElement<?> entities, boolean positive, int lineNumber) {
            return new PsiEntityIsInLavaConditionImpl(entities, positive, lineNumber);
        }
    }
}
