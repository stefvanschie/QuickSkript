package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiMaxHealthExpression;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the maximum amount of health a living entity may have. This cannot be pre-computed, since this may change during
 * gameplay.
 *
 * @since 0.1.0
 */
public class PsiMaxHealthExpressionImpl extends PsiMaxHealthExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param livingEntity the entity to get the maximum health of, see {@link #livingEntity}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiMaxHealthExpressionImpl(@NotNull PsiElement<?> livingEntity, int lineNumber) {
        super(livingEntity, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Double executeImpl(@Nullable Context context) {
        Attributable attributable = livingEntity.execute(context, Attributable.class);
        AttributeInstance attribute = attributable.getAttribute(Attribute.GENERIC_MAX_HEALTH);

        if (attribute == null) {
            throw new ExecutionException("Entity does not have a maximum health", lineNumber);
        }

        return attribute.getValue() / 2.0;
    }

    @Override
    public void add(@Nullable Context context, @NotNull PsiElement<?> object) {
        Attributable attributable = livingEntity.execute(context, Attributable.class);
        AttributeInstance attribute = attributable.getAttribute(Attribute.GENERIC_MAX_HEALTH);

        if (attribute == null) {
            throw new ExecutionException("Entity does not have a maximum health", lineNumber);
        }

        attribute.setBaseValue(attribute.getBaseValue() + object.execute(context, Number.class).doubleValue() * 2);
    }

    @Override
    public void remove(@Nullable Context context, @NotNull PsiElement<?> object) {
        Attributable attributable = livingEntity.execute(context, Attributable.class);
        AttributeInstance attribute = attributable.getAttribute(Attribute.GENERIC_MAX_HEALTH);

        if (attribute == null) {
            throw new ExecutionException("Entity does not have a maximum health", lineNumber);
        }

        attribute.setBaseValue(attribute.getBaseValue() - object.execute(context, Number.class).doubleValue() * 2);
    }

    @Override
    public void reset(@Nullable Context context) {
        Attributable attributable = livingEntity.execute(context, Attributable.class);
        AttributeInstance attribute = attributable.getAttribute(Attribute.GENERIC_MAX_HEALTH);

        if (attribute == null) {
            throw new ExecutionException("Entity does not have a maximum health", lineNumber);
        }

        attribute.setBaseValue(attribute.getDefaultValue());
    }

    @Override
    public void set(@Nullable Context context, @NotNull PsiElement<?> object) {
        Attributable attributable = livingEntity.execute(context, Attributable.class);
        AttributeInstance attribute = attributable.getAttribute(Attribute.GENERIC_MAX_HEALTH);

        if (attribute == null) {
            throw new ExecutionException("Entity does not have a maximum health", lineNumber);
        }

        attribute.setBaseValue(object.execute(context, Number.class).doubleValue() * 2);
    }

    /**
     * A factory for creating {@link PsiMaxHealthExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiMaxHealthExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiMaxHealthExpression create(@NotNull PsiElement<?> livingEntity, int lineNumber) {
            return new PsiMaxHealthExpressionImpl(livingEntity, lineNumber);
        }
    }
}
