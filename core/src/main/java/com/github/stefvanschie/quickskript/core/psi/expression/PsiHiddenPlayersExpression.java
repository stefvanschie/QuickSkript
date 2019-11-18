package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Gets the players that are currently hidden for the player. This cannot be pre-computed, since this may change during
 * gameplay.
 *
 * @since 0.1.0
 */
public class PsiHiddenPlayersExpression extends PsiElement<Collection<?>> {

    /**
     * The player to get their hidden players from
     */
    @NotNull
    protected final PsiElement<?> player;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to get their hidden players from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiHiddenPlayersExpression(@NotNull PsiElement<?> player, int lineNumber) {
        super(lineNumber);

        this.player = player;
    }

    /**
     * A factory for creating {@link PsiHiddenPlayersExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The patterns for parsing {@link PsiHiddenPlayersExpression}s
         */
        @NotNull
        private final SkriptPattern[] patterns = SkriptPattern.parse(
            "[(all [[of] the]|the)] hidden players (of|for) %players%",
            "[(all [[of] the]|the)] players hidden (from|for|by) %players%"
        );

        /**
         * Parses the {@link #patterns} and invokes this method with its types if the match succeeds
         *
         * @param player the player to get their hidden players from
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patterns")
        public PsiHiddenPlayersExpression parse(@NotNull PsiElement<?> player, int lineNumber) {
            return create(player, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param player the player to get their hidden players from
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiHiddenPlayersExpression create(@NotNull PsiElement<?> player, int lineNumber) {
            return new PsiHiddenPlayersExpression(player, lineNumber);
        }
    }
}
