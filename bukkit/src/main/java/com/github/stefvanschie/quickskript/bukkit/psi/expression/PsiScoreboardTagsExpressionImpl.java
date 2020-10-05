package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiScoreboardTagsExpression;
import com.github.stefvanschie.quickskript.core.psi.util.PsiCollection;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Gets a set of scoreboard tags from an entity. This cannot be pre-computed, since this may change during gameplay.
 *
 * @since 0.1.0
 */
public class PsiScoreboardTagsExpressionImpl extends PsiScoreboardTagsExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param entity the entity to get the scoreboard tags from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiScoreboardTagsExpressionImpl(@NotNull PsiElement<?> entity, int lineNumber) {
        super(entity, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Set<Text> executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return entity.execute(environment, context, Entity.class).getScoreboardTags().stream()
            .map(Text::parseLiteral)
            .collect(Collectors.toSet());
    }

    @Override
    public void add(@Nullable SkriptRunEnvironment environment, @Nullable Context context, @NotNull PsiElement<?> object) {
        Entity entity = this.entity.execute(environment, context, Entity.class);

        PsiCollection.toStreamForgiving(object.execute(environment, context))
            .map(Object::toString)
            .forEach(entity::addScoreboardTag);
    }

    @Override
    public void delete(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        entity.execute(environment, context, Entity.class).getScoreboardTags().clear();
    }

    @Override
    public void remove(@Nullable SkriptRunEnvironment environment, @Nullable Context context, @NotNull PsiElement<?> object) {
        Entity entity = this.entity.execute(environment, context, Entity.class);

        PsiCollection.toStreamForgiving(object.execute(environment, context))
            .map(Object::toString)
            .forEach(entity::removeScoreboardTag);
    }

    @Override
    public void reset(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        delete(environment, context);
    }

    @Override
    public void set(@Nullable SkriptRunEnvironment environment, @Nullable Context context, @NotNull PsiElement<?> object) {
        Entity entity = this.entity.execute(environment, context, Entity.class);

        entity.getScoreboardTags().clear();

        PsiCollection.toStreamForgiving(object.execute(environment, context))
            .map(Object::toString)
            .forEach(entity::addScoreboardTag);
    }

    /**
     * A factory for creating {@link PsiScoreboardTagsExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiScoreboardTagsExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiScoreboardTagsExpression create(@NotNull PsiElement<?> entity, int lineNumber) {
            return new PsiScoreboardTagsExpressionImpl(entity, lineNumber);
        }
    }
}
