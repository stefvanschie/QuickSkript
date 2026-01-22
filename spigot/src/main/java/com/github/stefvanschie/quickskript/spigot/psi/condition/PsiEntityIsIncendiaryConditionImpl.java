package com.github.stefvanschie.quickskript.spigot.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiEntityIsIncendiaryCondition;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Explosive;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether the entities are incendiary.
 *
 * @since 0.1.0
 */
public class PsiEntityIsIncendiaryConditionImpl extends PsiEntityIsIncendiaryCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param entities the entities to check if they are incendiary
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiEntityIsIncendiaryConditionImpl(@NotNull PsiElement<?> entities, boolean positive, int lineNumber) {
        super(entities, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return super.positive == super.entities.executeMulti(environment, context, Entity.class).test(entity ->
            entity instanceof Explosive explosive && explosive.isIncendiary());
    }

    /**
     * A factory for creating {@link PsiEntityIsIncendiaryConditionImpl}s.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiEntityIsIncendiaryCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        @Override
        public PsiEntityIsIncendiaryCondition create(
            @NotNull PsiElement<?> entities,
            boolean positive,
            int lineNumber
        ) {
            return new PsiEntityIsIncendiaryConditionImpl(entities, positive, lineNumber);
        }
    }
}
