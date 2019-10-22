package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiGravityExpression;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets whether an entity is affected by gravity. This cannot be pre computed, since this can be changed during game
 * play.
 *
 * @since 0.1.0
 */
public class PsiGravityExpressionImpl extends PsiGravityExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param entity the entity to get the gravity for, see {@link #entity}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiGravityExpressionImpl(@NotNull PsiElement<?> entity, int lineNumber) {
        super(entity, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        return entity.execute(context, Entity.class).hasGravity();
    }

    @Override
    public void reset(@Nullable Context context) {
        entity.execute(context, Entity.class).setGravity(true);
    }

    @Override
    public void set(@Nullable Context context, @NotNull PsiElement<?> object) {
        entity.execute(context, Entity.class).setGravity(object.execute(context, Boolean.class));
    }

    /**
     * A factory for creating {@link PsiGravityExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiGravityExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiGravityExpression create(@NotNull PsiElement<?> entity, int lineNumber) {
            return new PsiGravityExpressionImpl(entity, lineNumber);
        }
    }
}
