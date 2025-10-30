package com.github.stefvanschie.quickskript.paper.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiEntityIsInRainCondition;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the entity is in rain.
 *
 * @since 0.1.0
 */
public class PsiEntityIsInRainConditionImpl extends PsiEntityIsInRainCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param entities the entities to check if they are in rain
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiEntityIsInRainConditionImpl(@NotNull PsiElement<?> entities, boolean positive, int lineNumber) {
        super(entities, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return super.positive == super.entities.executeMulti(environment, context, Entity.class).test(Entity::isInRain);
    }

    /**
     * A factory for creating {@link PsiEntityIsInRainConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiEntityIsInRainCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiEntityIsInRainCondition create(@NotNull PsiElement<?> entities, boolean positive, int lineNumber) {
            return new PsiEntityIsInRainConditionImpl(entities, positive, lineNumber);
        }
    }
}
