package com.github.stefvanschie.quickskript.paper.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiEntityIsOfTypeCondition;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.registry.EntityTypeRegistry;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the provided entities are of the provided entity types.
 *
 * @since 0.1.0
 */
public class PsiEntityIsOfTypeConditionImpl extends PsiEntityIsOfTypeCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param entities the entities to check if they are of the provided entity types
     * @param entityTypes the entity types to check if they are the type of the provided entities
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiEntityIsOfTypeConditionImpl(@NotNull PsiElement<?> entities, @NotNull PsiElement<?> entityTypes,
                                           boolean positive, int lineNumber) {
        super(entities, entityTypes, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        MultiResult<? extends EntityTypeRegistry.Entry> entityTypes = super.entityTypes.executeMulti(environment,
            context, EntityTypeRegistry.Entry.class);
        MultiResult<? extends Entity> entities = super.entities.executeMulti(environment, context, Entity.class);

        return super.positive == entityTypes.map(entityType -> {
            String key = entityType.getKey();

            if (key == null) {
                return EntityType.UNKNOWN;
            }

            NamespacedKey namespacedKey = NamespacedKey.fromString(key);

            if (namespacedKey == null) {
                throw new ExecutionException("'" + key + "' is not a valid namespaced key",
                    super.lineNumber);
            }

            return Registry.ENTITY_TYPE.get(namespacedKey);
        }).test(entityType -> entities.test(entity -> entity.getType() == entityType));
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
        public PsiEntityIsOfTypeCondition create(@NotNull PsiElement<?> entities, @NotNull PsiElement<?> entityTypes,
                                                 boolean positive, int lineNumber) {
            return new PsiEntityIsOfTypeConditionImpl(entities, entityTypes, positive, lineNumber);
        }
    }
}
