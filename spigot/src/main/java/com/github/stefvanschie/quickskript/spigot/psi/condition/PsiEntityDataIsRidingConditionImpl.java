package com.github.stefvanschie.quickskript.spigot.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiEntityDataIsRidingCondition;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.spigot.util.entitydata.EntityDataImpl;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether the entities are riding an entity.
 *
 * @since 0.1.0
 */
public class PsiEntityDataIsRidingConditionImpl extends PsiEntityDataIsRidingCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param entities the entities to check if they are riding an entity
     * @param entityDatas the entity datas the provided entities must be riding
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiEntityDataIsRidingConditionImpl(@NotNull PsiElement<?> entities, @NotNull PsiElement<?> entityDatas,
                                               boolean positive, int lineNumber) {
        super(entities, entityDatas, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        MultiResult<? extends Entity> entities = super.entities.executeMulti(environment, context, Entity.class);
        MultiResult<? extends EntityDataImpl> entityDatas = super.entityDatas.executeMulti(environment, context,
            EntityDataImpl.class);

        return super.positive == entities.map(Entity::getVehicle).test(vehicle -> {
            if (vehicle == null) {
                return false;
            }

            return entityDatas.test(entityData -> entityData.matches(vehicle));
        });
    }

    /**
     * A factory to create instances of {@link PsiEntityDataIsRidingConditionImpl}.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiEntityDataIsRidingCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _, _ -> new", pure = true)
        @Override
        public PsiEntityDataIsRidingCondition create(@NotNull PsiElement<?> entities,
                                                     @NotNull PsiElement<?> entityDatas, boolean positive,
                                                     int lineNumber) {
            return new PsiEntityDataIsRidingConditionImpl(entities, entityDatas, positive, lineNumber);
        }
    }
}
