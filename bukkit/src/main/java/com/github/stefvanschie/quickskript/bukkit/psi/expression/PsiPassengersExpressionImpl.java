package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiPassengersExpression;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Gets the passengers of an entity
 *
 * @since 0.1.0
 */
public class PsiPassengersExpressionImpl extends PsiPassengersExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param entity the entity to get the passengers of
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiPassengersExpressionImpl(PsiElement<?> entity, int lineNumber) {
        super(entity, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected List<Entity> executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return entity.execute(environment, context, Entity.class).getPassengers();
    }

    @Override
    public void add(@Nullable SkriptRunEnvironment environment, @Nullable Context context, @NotNull PsiElement<?> object) {
        entity.execute(environment, context, Entity.class).getPassengers().add(object.execute(environment, context, Entity.class));
    }

    @Override
    public void delete(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        entity.execute(environment, context, Entity.class).eject();
    }

    @Override
    public void remove(@Nullable SkriptRunEnvironment environment, @Nullable Context context, @NotNull PsiElement<?> object) {
        Entity entity = this.entity.execute(environment, context, Entity.class);
        Entity searchingEntity = object.execute(environment, context, Entity.class);

        entity.getPassengers().stream()
            .filter(passenger -> passenger.equals(searchingEntity))
            .forEach(entity::removePassenger);
    }

    @Override
    public void removeAll(@Nullable SkriptRunEnvironment environment, @Nullable Context context, @NotNull PsiElement<?> object) {
        remove(environment, context, object);
    }

    @Override
    public void reset(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        delete(environment, context);
    }

    @Override
    public void set(@Nullable SkriptRunEnvironment environment, @Nullable Context context, @NotNull PsiElement<?> object) {
        Entity entity = this.entity.execute(environment, context, Entity.class);

        entity.eject();
        entity.addPassenger(object.execute(environment, context, Entity.class));
    }

    /**
     * A factory for creating {@link PsiPassengersExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiPassengersExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiPassengersExpression create(@NotNull PsiElement<?> entity, int lineNumber) {
            return new PsiPassengersExpressionImpl(entity, lineNumber);
        }
    }
}
