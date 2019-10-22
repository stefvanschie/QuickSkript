package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiHealthExpression;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Damageable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the amount of health a damageable has. This cannot be pore computed, since this may change during game play.
 *
 * @since 0.1.0
 */
public class PsiHealthExpressionImpl extends PsiHealthExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param damageable the damageable to get the health from, see {@link #damageable}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiHealthExpressionImpl(@NotNull PsiElement<?> damageable, int lineNumber) {
        super(damageable, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Double executeImpl(@Nullable Context context) {
        return damageable.execute(context, Damageable.class).getHealth() / 2.0;
    }

    @Override
    public void add(@Nullable Context context, @NotNull PsiElement<?> object) {
        Damageable damageable = this.damageable.execute(context, Damageable.class);

        damageable.setHealth(damageable.getHealth() + object.execute(context, Number.class).doubleValue() * 2.0);
    }

    @Override
    public void delete(@Nullable Context context) {
        damageable.execute(context, Damageable.class).setHealth(0);
    }

    @Override
    public void remove(@Nullable Context context, @NotNull PsiElement<?> object) {
        Damageable damageable = this.damageable.execute(context, Damageable.class);

        damageable.setHealth(damageable.getHealth() - object.execute(context, Number.class).doubleValue() * 2.0);
    }

    @Override
    public void reset(@Nullable Context context) {
        Object object = damageable.execute(context);

        if (!(object instanceof Attributable) || !(object instanceof Damageable)) {
            throw new ExecutionException("Object must be both an Attributable and a Damageable", lineNumber);
        }

        AttributeInstance attribute = ((Attributable) object).getAttribute(Attribute.GENERIC_MAX_HEALTH);

        if (attribute == null) {
            throw new ExecutionException("Could not find necessary attribute for object", lineNumber);
        }

        ((Damageable) object).setHealth(attribute.getBaseValue());
    }

    @Override
    public void set(@Nullable Context context, @NotNull PsiElement<?> object) {
        double health = object.execute(context, Number.class).doubleValue();

        damageable.execute(context, Damageable.class).setHealth(health * 2.0);
    }

    /**
     * A factory for creating {@link PsiHealthExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiHealthExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiHealthExpression create(@NotNull PsiElement<?> damageable, int lineNumber) {
            return new PsiHealthExpressionImpl(damageable, lineNumber);
        }
    }
}
