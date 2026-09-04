package com.github.stefvanschie.quickskript.paper.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsRidingCondition;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether the entities are riding an entity.
 *
 * @since 0.1.0
 */
public class PsiIsRidingConditionImpl extends PsiIsRidingCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param entities the entities to check if they are riding an entity
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsRidingConditionImpl(@NotNull PsiElement<?> entities, boolean positive, int lineNumber) {
        super(entities, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        MultiResult<? extends Entity> entities = super.entities.executeMulti(environment, context, Entity.class);

        return super.positive == entities.test(entity -> entity.getVehicle() != null);
    }

    /**
     * A factory to create instances of {@link PsiIsRidingConditionImpl}.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsRidingCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        @Override
        public PsiIsRidingCondition create(@NotNull PsiElement<?> entities, boolean positive, int lineNumber) {
            return new PsiIsRidingConditionImpl(entities, positive, lineNumber);
        }
    }
}
