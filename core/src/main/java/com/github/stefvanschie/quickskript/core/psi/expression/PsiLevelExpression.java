package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.expression.util.*;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Gets the experience level from a player. This cannot be pre-computed, since this may change during gameplay.
 *
 * @since 0.1.0
 */
public class PsiLevelExpression extends PsiElement<Integer> implements Addable, Deletable, Removable, Resettable,
    Settable {

    /**
     * The player to get the level from
     */
    @NotNull
    protected final PsiElement<?> player;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to get the level from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiLevelExpression(@NotNull PsiElement<?> player, int lineNumber) {
        super(lineNumber);

        this.player = player;
    }

    /**
     * A factory for creating {@link PsiLevelExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The patterns for matching {@link PsiLevelExpression}s
         */
        @NotNull
        private final SkriptPattern[] patterns = SkriptPattern.parse(
            "[the] level of %players%",
            "%players%'[s] level"
        );

        /**
         * Parses the {@link #patterns} and invokes this method with its types if the match succeeds
         *
         * @param player the player to get the level from
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patterns")
        public PsiLevelExpression parse(@NotNull PsiElement<?> player, int lineNumber) {
            return create(player, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param player the player to get the level from
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiLevelExpression create(@NotNull PsiElement<?> player, int lineNumber) {
            return new PsiLevelExpression(player, lineNumber);
        }
    }
}
