package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiVehicleExpression;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the vehicle of an entity, or null if the entity doesn't have a vehicle.
 *
 * @since 0.1.0
 */
public class PsiVehicleExpressionImpl extends PsiVehicleExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param entity the entity to get the vehicle from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiVehicleExpressionImpl(@NotNull PsiElement<?> entity, int lineNumber) {
        super(entity, lineNumber);
    }

    @Nullable
    @Contract(pure = true)
    @Override
    protected Entity executeImpl(@Nullable Context context) {
        return entity.execute(context, Entity.class).getVehicle();
    }

    /**
     * A factory for creating {@link PsiVehicleExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiVehicleExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiVehicleExpression create(@NotNull PsiElement<?> entity, int lineNumber) {
            return new PsiVehicleExpressionImpl(entity, lineNumber);
        }
    }
}
