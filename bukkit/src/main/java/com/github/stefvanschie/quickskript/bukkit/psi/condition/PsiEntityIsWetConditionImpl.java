package com.github.stefvanschie.quickskript.bukkit.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiEntityIsWetCondition;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if an entity is wet.
 *
 * @since 0.1.0
 */
public class PsiEntityIsWetConditionImpl extends PsiEntityIsWetCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param entities the entities to check if they are wet
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiEntityIsWetConditionImpl(@NotNull PsiElement<?> entities, boolean positive, int lineNumber) {
        super(entities, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        MultiResult<? extends Entity> entities = super.entities.executeMulti(environment, context, Entity.class);

        return super.positive == entities.test(Entity::isInWaterOrRainOrBubbleColumn);
    }

    /**
     * A factory for creating {@link PsiEntityIsWetConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiEntityIsWetCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiEntityIsWetCondition create(@NotNull PsiElement<?> entities, boolean positive, int lineNumber) {
            return new PsiEntityIsWetConditionImpl(entities, positive, lineNumber);
        }
    }
}
