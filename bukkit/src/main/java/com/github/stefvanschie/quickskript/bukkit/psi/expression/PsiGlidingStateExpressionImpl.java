package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiGlidingStateExpression;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the gliding state of an entity
 *
 * @since 0.1.0
 */
public class PsiGlidingStateExpressionImpl extends PsiGlidingStateExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param livingEntity the living entity to get the gliding state from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiGlidingStateExpressionImpl(@NotNull PsiElement<?> livingEntity, int lineNumber) {
        super(livingEntity, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        return livingEntity.execute(context, LivingEntity.class).isGliding();
    }

    @Override
    public void reset(@Nullable Context context) {
        livingEntity.execute(context, LivingEntity.class).setGliding(false);
    }

    @Override
    public void set(@Nullable Context context, @NotNull PsiElement<?> object) {
        livingEntity.execute(context, LivingEntity.class).setGliding(object.execute(context, Boolean.class));
    }

    /**
     * A factory for creating {@link PsiGlidingStateExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiGlidingStateExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiGlidingStateExpression create(@NotNull PsiElement<?> entity, int lineNumber) {
            return new PsiGlidingStateExpressionImpl(entity, lineNumber);
        }
    }
}
