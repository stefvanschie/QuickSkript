package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Nullable
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        throw new UnsupportedOperationException("Cannot execute expression without implementation.");
    }

    /**
     * A factory for creating {@link PsiCanFlyCondition}s.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiCanFlyCondition> {

        /**
         * The pattern for matching positive can fly conditions
         */
        @NotNull
        private final Pattern positivePattern = Pattern.compile("(?<player>[\\s\\S]+) can fly");

        /**
         * The pattern for matching negative can fly conditions
         */
        @NotNull
        private final Pattern negativePattern = Pattern.compile("(?<player>[\\s\\S]+) (?:can't|cannot|can not) fly");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Contract(pure = true)
        @Override
        public PsiCanFlyCondition tryParse(@NotNull String text, int lineNumber) {
            var skriptLoader = SkriptLoader.get();
            Matcher positiveMatcher = positivePattern.matcher(text);

            if (positiveMatcher.matches()) {
                String playerGroup = positiveMatcher.group("player");
                PsiElement<?> player = skriptLoader.forceParseElement(playerGroup, lineNumber);

                return create(player, true, lineNumber);
            }

            Matcher negativeMatcher = negativePattern.matcher(text);

            if (negativeMatcher.matches()) {
                String playerGroup = negativeMatcher.group("player");
                PsiElement<?> player = skriptLoader.forceParseElement(playerGroup, lineNumber);

                return create(player, false, lineNumber);
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
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
