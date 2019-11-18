package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.expression.util.Deletable;
import com.github.stefvanschie.quickskript.core.psi.expression.util.Resettable;
import com.github.stefvanschie.quickskript.core.psi.expression.util.Settable;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Gets the header of the player list for a player. This cannot be pre-computed, since this may change during gameplay.
 *
 * @since 0.1.0
 */
public class PsiPlayerListHeaderExpression extends PsiElement<Text> implements Deletable, Resettable, Settable {

    /**
     * The player to get the header for
     */
    @NotNull
    protected PsiElement<?> player;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to get the header for
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiPlayerListHeaderExpression(@NotNull PsiElement<?> player, int lineNumber) {
        super(lineNumber);

        this.player = player;
    }

    /**
     * A factory for creating {@link PsiPlayerListHeaderExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiPlayerListHeaderExpression}s
         */
        @NotNull
        private SkriptPattern[] patterns = SkriptPattern.parse(
            "[the] (player|tab)[ ]list header [text|message] of %players%",
            "%players%'[s] (player|tab)[ ]list header [text|message]"
        );

        /**
         * Parses the {@link #patterns} and invokes this method with its types if the match succeeds
         *
         * @param player the player to get the header for
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patterns")
        public PsiPlayerListHeaderExpression parse(@NotNull PsiElement<?> player, int lineNumber) {
            return create(player, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param player the player to get the header for
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiPlayerListHeaderExpression create(@NotNull PsiElement<?> player, int lineNumber) {
            return new PsiPlayerListHeaderExpression(player, lineNumber);
        }
    }
}
