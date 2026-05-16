package com.github.stefvanschie.quickskript.paper.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiEntityIsPersistentCondition;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the entities are persistent.
 *
 * @since 0.1.0
 */
public class PsiEntityIsPersistentConditionImpl extends PsiEntityIsPersistentCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param entities the entities to check if they are persistent
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiEntityIsPersistentConditionImpl(@NotNull PsiElement<?> entities, boolean positive, int lineNumber) {
        super(entities, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return super.positive == super.entities.executeMulti(environment, context, Entity.class)
            .test(Entity::isPersistent);
    }

    /**
     * A factory for creating instances of {@link PsiEntityIsPersistentConditionImpl}.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiEntityIsPersistentCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        @Override
        public PsiEntityIsPersistentCondition create(@NotNull PsiElement<?> entities, boolean positive,
                                                     int lineNumber) {
            return new PsiEntityIsPersistentConditionImpl(entities, positive, lineNumber);
        }
    }
}
