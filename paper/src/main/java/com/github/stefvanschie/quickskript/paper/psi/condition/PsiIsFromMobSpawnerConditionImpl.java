package com.github.stefvanschie.quickskript.paper.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsFromMobSpawnerCondition;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether the entities were spawned by a mob spawner.
 *
 * @since 0.1.0
 */
public class PsiIsFromMobSpawnerConditionImpl extends PsiIsFromMobSpawnerCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param entities the entities to check if they were spawned by a mob spawner
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsFromMobSpawnerConditionImpl(@NotNull PsiElement<?> entities, boolean positive, int lineNumber) {
        super(entities, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return super.positive == super.entities.executeMulti(environment, context, Entity.class)
            .test(Entity::fromMobSpawner);
    }

    /**
     * A factory for creating {@link PsiIsFromMobSpawnerCondition}s.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsFromMobSpawnerCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        @Override
        public PsiIsFromMobSpawnerCondition create(@NotNull PsiElement<?> entities, boolean positive, int lineNumber) {
            return new PsiIsFromMobSpawnerConditionImpl(entities, positive, lineNumber);
        }
    }
}
