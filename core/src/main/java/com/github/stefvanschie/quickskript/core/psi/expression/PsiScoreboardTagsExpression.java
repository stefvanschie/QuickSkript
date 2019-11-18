package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.expression.util.*;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Gets a set of scoreboard tags from an entity. This cannot be pre-computed, since this may change during gameplay.
 *
 * @since 0.1.0
 */
public class PsiScoreboardTagsExpression extends PsiElement<Set<Text>> implements Addable, Deletable, Removable,
    Resettable, Settable {

    /**
     * The entity to get the scoreboard tags from
     */
    @NotNull
    protected PsiElement<?> entity;

    /**
     * Creates a new element with the given line number
     *
     * @param entity the entity to get the scoreboard tags from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiScoreboardTagsExpression(@NotNull PsiElement<?> entity, int lineNumber) {
        super(lineNumber);

        this.entity = entity;
    }

    /**
     * A factory for creating {@link PsiScoreboardTagsExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiScoreboardTagsExpression}s
         */
        @NotNull
        private SkriptPattern[] patterns = SkriptPattern.parse(
            "[all [[of] the]|the] scoreboard tags of %entities%",
            "%entities%'[s] scoreboard tags"
        );

        /**
         * Parses the {@link #patterns} and invokes this method with its types if the match succeeds
         *
         * @param entity the entity to get the scoreboard tags from
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patterns")
        public PsiScoreboardTagsExpression parse(@NotNull PsiElement<?> entity, int lineNumber) {
            return create(entity, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiScoreboardTagsExpression create(@NotNull PsiElement<?> entity, int lineNumber) {
            return new PsiScoreboardTagsExpression(entity, lineNumber);
        }
    }
}
