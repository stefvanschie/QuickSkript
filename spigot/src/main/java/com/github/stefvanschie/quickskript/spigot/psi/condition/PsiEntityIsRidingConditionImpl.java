package com.github.stefvanschie.quickskript.spigot.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiEntityIsRidingCondition;
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
public class PsiEntityIsRidingConditionImpl extends PsiEntityIsRidingCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param entities the entities to check if they are riding an entity
     * @param vehicles the vehicles the provided entities must be riding
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiEntityIsRidingConditionImpl(@NotNull PsiElement<?> entities, @NotNull PsiElement<?> vehicles,
                                           boolean positive, int lineNumber) {
        super(entities, vehicles, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        MultiResult<? extends Entity> entities = super.entities.executeMulti(environment, context, Entity.class);
        MultiResult<? extends Entity> vehicles = super.vehicles.executeMulti(environment, context, Entity.class);

        return super.positive == entities.map(Entity::getVehicle).test(vehicle -> {
            if (vehicle == null) {
                return false;
            }

            return vehicles.test(vehicle::equals);
        });
    }

    /**
     * A factory to create instances of {@link PsiEntityIsRidingConditionImpl}.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiEntityIsRidingCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _, _ -> new", pure = true)
        @Override
        public PsiEntityIsRidingCondition create(@NotNull PsiElement<?> entities, @NotNull PsiElement<?> vehicles,
                                                 boolean positive, int lineNumber) {
            return new PsiEntityIsRidingConditionImpl(entities, vehicles, positive, lineNumber);
        }
    }
}
