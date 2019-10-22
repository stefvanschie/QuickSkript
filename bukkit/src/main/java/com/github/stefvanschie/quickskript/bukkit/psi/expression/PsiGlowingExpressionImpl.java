package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiGlowingExpression;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PsiGlowingExpressionImpl extends PsiGlowingExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param entity the entity to get the glowing state of, see {@link #entity}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiGlowingExpressionImpl(@NotNull PsiElement<?> entity, int lineNumber) {
        super(entity, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        return entity.execute(context, Entity.class).isGlowing();
    }

    @Override
    public void reset(@Nullable Context context) {
        entity.execute(context, Entity.class).setGlowing(false);
    }

    @Override
    public void set(@Nullable Context context, @NotNull PsiElement<?> object) {
        entity.execute(context, Entity.class).setGlowing(object.execute(context, Boolean.class));
    }

    /**
     * A factory for creating {@link PsiGlowingExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiGlowingExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiGlowingExpression create(@NotNull PsiElement<?> entity, int lineNumber) {
            return new PsiGlowingExpressionImpl(entity, lineNumber);
        }
    }
}
