package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks whether a player can see another player. This cannot be pre computed, since this may change during game play.
 *
 * @since 0.1.0
 */
public class PsiCanSeeCondition extends PsiElement<Boolean> {

    /**
     * The player and the target player
     */
    @NotNull
    protected final PsiElement<?> player, targetPlayer;

    /**
     * If false, the result of this execution should be negated.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player
     * @param targetPlayer the player to test against
     * @param positive false if the result should be negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiCanSeeCondition(@NotNull PsiElement<?> player, @NotNull PsiElement<?> targetPlayer, boolean positive,
                                 int lineNumber) {
        super(lineNumber);

        this.player = player;
        this.targetPlayer = targetPlayer;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiCanSeeCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * A pattern for matching positive can see conditions
         */
        @NotNull
        private final SkriptPattern[] positivePatterns = SkriptPattern.parse(
                "%players% (is|are) visible for %players%",
                "%players% (is|are)(n't| not) invisible for %players%",
                "%players% can see %players%");

        /**
         * A pattern for matching negative can see conditions
         */
        @NotNull
        private final SkriptPattern[] negativePatterns = SkriptPattern.parse(
                "%players% (is|are) invisible for %players%",
                "%players% (is|are)(n't| not) visible for %players%",
                "%players% can('t| not) see %players%");

        /**
         * Parses the {@link #positivePatterns} and invokes this method with its types if the match succeeds
         *
         * @param player the player
         * @param target the player to test against
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePatterns")
        private PsiCanSeeCondition parsePositive(@NotNull PsiElement<?> player, @NotNull PsiElement<?> target,
                                                int lineNumber) {
            return create(player, target, true, lineNumber);
        }

        /**
         * Parses the {@link #negativePatterns} and invokes this method with its types if the match succeeds
         *
         * @param player the player
         * @param target the player to test against
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePatterns")
        private PsiCanSeeCondition parseNegative(@NotNull PsiElement<?> player, @NotNull PsiElement<?> target,
                                                int lineNumber) {
            return create(player, target, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param player the player
         * @param targetPlayer the player to test against
         * @param positive false if the result should be negated
         * @param lineNumber the line number
         * @return the can see condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiCanSeeCondition create(@NotNull PsiElement<?> player, @NotNull PsiElement<?> targetPlayer,
                                         boolean positive, int lineNumber) {
            return new PsiCanSeeCondition(player, targetPlayer, positive, lineNumber);
        }
    }
}
