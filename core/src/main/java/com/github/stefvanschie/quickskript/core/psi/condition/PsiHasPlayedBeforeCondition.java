package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks whether a player has played before. This cannot be pre computed, since players can log out and log in,
 * changing the result of this element.
 *
 * @since 0.1.0
 */
public class PsiHasPlayedBeforeCondition extends PsiElement<Boolean> {

    /**
     * The (offline)player to check
     */
    @NotNull
    protected final PsiElement<?> player;

    /**
     * True if the result stays the same, false if it needs to be inverted.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param player the (offline)player to check for, see {@link #player}
     * @param positive true if the result stays the same, false if the result needs to be inverted, see
     *                 {@link #positive}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiHasPlayedBeforeCondition(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
        super(lineNumber);

        this.player = player;
        this.positive = positive;
    }

    /**
     * A factory for creating psi has played before conditions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern to match positive has played before conditions
         */
        @NotNull
        private final SkriptPattern positivePattern = SkriptPattern
            .parse("%offline player% [(has|did)] [already] play[ed] [on (this|the) server] (before|already)");

        /**
         * The pattern to match negative has played before conditions
         */
        @NotNull
        private final SkriptPattern negativePattern = SkriptPattern.parse(
            "%offline player% (has not|hasn't|did not|didn't) [(already|yet)] play[ed] [on (this|the) server] (before|already|yet)"
        );

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param offlinePlayer the player to check
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        public PsiHasPlayedBeforeCondition parsePositive(@NotNull PsiElement<?> offlinePlayer, int lineNumber) {
            return create(offlinePlayer, true, lineNumber);
        }

        /**
         * Parses the {@link #negativePattern} and invokes this method with its types if the match succeeds
         *
         * @param offlinePlayer the player to check
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        public PsiHasPlayedBeforeCondition parseNegative(@NotNull PsiElement<?> offlinePlayer, int lineNumber) {
            return create(offlinePlayer, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param player the player to check
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        protected PsiHasPlayedBeforeCondition create(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
            return new PsiHasPlayedBeforeCondition(player, positive, lineNumber);
        }
    }
}
