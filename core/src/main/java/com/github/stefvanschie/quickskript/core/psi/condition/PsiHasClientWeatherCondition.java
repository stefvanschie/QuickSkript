package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks whether the player has a custom type of weather. This cannot be pre computed, since this may change during
 * game play.
 *
 * @since 0.1.0
 */
public class PsiHasClientWeatherCondition extends PsiElement<Boolean> {

    /**
     * The player to check the weather for
     */
    @NotNull
    protected final PsiElement<?> player;

    /**
     * False if the result should be negated
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to check for
     * @param positive false if the result should be negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiHasClientWeatherCondition(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
        super(lineNumber);

        this.player = player;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiHasClientWeatherCondition}s.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching positive {@link PsiHasClientWeatherCondition}s
         */
        @NotNull
        private final SkriptPattern positivePattern =
            SkriptPattern.parse("%players% (has|have) [a] (client|custom) weather [set]");

        /**
         * The pattern for matching negative {@link PsiHasClientWeatherCondition}s
         */
        @NotNull
        private final SkriptPattern negativePattern =
            SkriptPattern.parse("%players% (doesn't|does not|do not|don't) have [a] (client|custom) weather [set]");

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param player the player to check the weather for
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        public PsiHasClientWeatherCondition parsePositive(@NotNull PsiElement<?> player, int lineNumber) {
            return create(player, true, lineNumber);
        }

        /**
         * Parses the {@link #negativePattern} and invokes this method with its types if the match succeeds
         *
         * @param player the player to check the weather for
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        public PsiHasClientWeatherCondition parseNegative(@NotNull PsiElement<?> player, int lineNumber) {
            return create(player, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param player the player to check the weather for
         * @param positive false if the result should be negated
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiHasClientWeatherCondition create(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
            return new PsiHasClientWeatherCondition(player, positive, lineNumber);
        }
    }
}
