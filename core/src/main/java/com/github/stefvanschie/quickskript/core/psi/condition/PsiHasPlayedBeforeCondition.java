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
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Nullable
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        throw new UnsupportedOperationException("Cannot execute expression without implementation.");
    }

    /**
     * A factory for creating psi has played before conditions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiHasPlayedBeforeCondition> {

        /**
         * The pattern to match positive has played before conditions
         */
        @NotNull
        private final Pattern positivePattern = Pattern.compile(
            "(?<player>[\\s\\S]+?) (?:(?:has|did) )?(?:already )?play(?:ed)? (?:on (?:this|the) server )?(?:before|already)"
        );

        /**
         * The pattern to match negative has played before conditions
         */
        @NotNull
        private final Pattern negativePattern = Pattern.compile(
            "(?<player>[\\s\\S]+) (?:has not|hasn't|did not|didn't) (?:(?:already|yet) )?play(?:ed)? (?:on (?:this|the) server )?(?:before|already|yet)"
        );

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Contract(pure = true)
        @Override
        public PsiHasPlayedBeforeCondition tryParse(@NotNull String text, int lineNumber) {
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
