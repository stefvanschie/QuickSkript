package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if the specified player is able to fly. This cannot be pre computed, since this value may change during
 * game play.
 *
 * @since 0.1.0
 */
public class PsiCanFlyCondition extends PsiElement<Boolean> {

    /**
     * The player to check if they can fly
     */
    @NotNull
    protected final PsiElement<?> player;

    /**
     * If this value is true, the result of the computation will stay the same. Otherwise, the result will be inverted.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to test for
     * @param positive whether the result needs to be inverted or not, see {@link #positive}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiCanFlyCondition(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
        super(lineNumber);

        this.player = player;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiCanFlyCondition}s.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching positive can fly conditions
         */
        @NotNull
        private final SkriptPattern positivePattern = SkriptPattern.parse("%players% can fly");

        /**
         * The pattern for matching negative can fly conditions
         */
        @NotNull
        private final SkriptPattern negativePattern = SkriptPattern.parse("%players% (can't|cannot|can not) fly");

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param player the player to test flight for
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        private PsiCanFlyCondition parsePositive(@NotNull PsiElement<?> player, int lineNumber) {
            return create(player, true, lineNumber);
        }

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param player the player to test flight for
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        private PsiCanFlyCondition parseNegative(@NotNull PsiElement<?> player, int lineNumber) {
            return create(player, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param player the player to test for
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        protected PsiCanFlyCondition create(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
            return new PsiCanFlyCondition(player, positive, lineNumber);
        }
    }
}
