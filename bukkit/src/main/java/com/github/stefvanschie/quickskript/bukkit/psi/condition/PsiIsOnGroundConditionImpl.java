package com.github.stefvanschie.quickskript.bukkit.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsOnGroundCondition;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether an entity is on the ground. This cannot be pre computed, since entities can land or come off of the
 * ground during game play.
 *
 * @since 0.1.0
 */
public class PsiIsOnGroundConditionImpl extends PsiIsOnGroundCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param entity the entity to check whether they are on the ground
     * @param positive false if the result of this execution needs to be inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsOnGroundConditionImpl(@NotNull PsiElement<?> entity, boolean positive, int lineNumber) {
        super(entity, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        return positive == entity.execute(context, Entity.class).isOnGround();
    }

    /**
     * A factory for creating {@link PsiIsOnGroundConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsOnGroundCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        protected PsiIsOnGroundCondition create(@NotNull PsiElement<?> entity, boolean positive, int lineNumber) {
            return new PsiIsOnGroundConditionImpl(entity, positive, lineNumber);
        }
    }
}
