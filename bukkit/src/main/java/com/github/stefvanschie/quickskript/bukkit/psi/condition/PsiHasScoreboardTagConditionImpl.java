package com.github.stefvanschie.quickskript.bukkit.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiHasScoreboardTagCondition;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks to see if the specified entity has a specific scoreboard tag. This cannot be pre computed, since this may
 * change during game play.
 *
 * @since 0.1.0
 */
public class PsiHasScoreboardTagConditionImpl extends PsiHasScoreboardTagCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param entity     the entity to check, see {@link #entity}
     * @param tag        the tag that we need to find, see {@link #tag}
     * @param positive   false if the result needs to be inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiHasScoreboardTagConditionImpl(@NotNull PsiElement<?> entity, @NotNull PsiElement<?> tag,
                                               boolean positive, int lineNumber) {
        super(entity, tag, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        Entity entity = this.entity.execute(context, Entity.class);

        return positive == entity.getScoreboardTags().contains(tag.execute(context, Text.class).toString());
    }

    /**
     * A factory for creating {@link PsiHasScoreboardTagConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiHasScoreboardTagCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiHasScoreboardTagCondition create(@NotNull PsiElement<?> entity, @NotNull PsiElement<?> tag,
                                                   boolean positive, int lineNumber) {
            return new PsiHasScoreboardTagConditionImpl(entity, tag, positive, lineNumber);
        }
    }
}
