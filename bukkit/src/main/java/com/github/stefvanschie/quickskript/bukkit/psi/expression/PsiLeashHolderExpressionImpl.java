package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiLeashHolderExpression;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the entity who's currently leashing the entity. This cannot be pre-computed, since this may change during
 * gameplay.
 *
 * @since 0.1.0
 */
public class PsiLeashHolderExpressionImpl extends PsiLeashHolderExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param entity the leashed entity to get the holder from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiLeashHolderExpressionImpl(@NotNull PsiElement<?> entity, int lineNumber) {
        super(entity, lineNumber);
    }

    @Nullable
    @Contract(pure = true)
    @Override
    protected Entity executeImpl(@Nullable Context context) {
        LivingEntity entity = this.entity.execute(context, LivingEntity.class);

        return entity.isLeashed() ? entity.getLeashHolder() : null;
    }

    /**
     * A factory for creating {@link PsiLeashHolderExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiLeashHolderExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiLeashHolderExpression create(@NotNull PsiElement<?> entity, int lineNumber) {
            return new PsiLeashHolderExpressionImpl(entity, lineNumber);
        }
    }
}
