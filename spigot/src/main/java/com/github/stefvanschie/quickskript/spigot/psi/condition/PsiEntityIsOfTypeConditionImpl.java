package com.github.stefvanschie.quickskript.spigot.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiEntityIsOfTypeCondition;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.spigot.util.entitydata.EntityDataImpl;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the provided entities match the provided entity datas.
 *
 * @since 0.1.0
 */
public class PsiEntityIsOfTypeConditionImpl extends PsiEntityIsOfTypeCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param entities the entities to check if they match the provided entity datas
     * @param entityDatas the entity types to check if they match the provided entities
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiEntityIsOfTypeConditionImpl(@NotNull PsiElement<?> entities, @NotNull PsiElement<?> entityDatas,
                                           boolean positive, int lineNumber) {
        super(entities, entityDatas, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        MultiResult<? extends EntityDataImpl> entityDatas = super.entityDatas.executeMulti(environment, context,
            EntityDataImpl.class);
        MultiResult<? extends Entity> entities = super.entities.executeMulti(environment, context, Entity.class);

        return super.positive == entityDatas.test(entityData ->
            entities.test(entityData::matches));
    }

    /**
     * A factory to create instances of {@link PsiEntityIsOfTypeConditionImpl}.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiEntityIsOfTypeCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _, _ -> new", pure = true)
        @Override
        public PsiEntityIsOfTypeCondition create(@NotNull PsiElement<?> entities, @NotNull PsiElement<?> entityDatas,
                                                 boolean positive, int lineNumber) {
            return new PsiEntityIsOfTypeConditionImpl(entities, entityDatas, positive, lineNumber);
        }
    }
}
