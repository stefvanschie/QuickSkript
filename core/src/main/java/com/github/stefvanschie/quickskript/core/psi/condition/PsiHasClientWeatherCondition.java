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
 * Checks whether the player has a custom type of weather. This cannot be pre computed, since this may change during
 * game play.
 *
 * @since 0.1.0
 */
public class PsiHasClientWeatherCondition extends PsiElement<Boolean> {

    /**
     * The player to check the weather for
     */
    protected PsiElement<?> player;

    /**
     * False if the result should be negated
     */
    protected boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to check for
     * @param positive false if the result should be negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiHasClientWeatherCondition(PsiElement<?> player, boolean positive, int lineNumber) {
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
     * A factory for creating {@link PsiHasClientWeatherCondition}s.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiHasClientWeatherCondition> {

        /**
         * The pattern for matching positive {@link PsiHasClientWeatherCondition}s
         */
        private final Pattern positivePattern = Pattern.compile("([\\s\\S]+) (?:has|have) (?:a )?(?:client|custom) weather(?: set)?");

        /**
         * The pattern for matching negative {@link PsiHasClientWeatherCondition}s
         */
        private final Pattern negativePattern = Pattern.compile("([\\s\\S]+) (?:doesn't|does not|do not|don't) have (?:a )?(?:client|custom) weather(?: set)?");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Contract(pure = true)
        @Override
        public PsiHasClientWeatherCondition tryParse(@NotNull String text, int lineNumber) {
            Matcher positiveMatcher = positivePattern.matcher(text);

            if (positiveMatcher.matches()) {
                PsiElement<?> player = SkriptLoader.get().forceParseElement(positiveMatcher.group(1), lineNumber);

                return create(player, true, lineNumber);
            }

            Matcher negativeeMatcher = negativePattern.matcher(text);

            if (negativeeMatcher.matches()) {
                PsiElement<?> player = SkriptLoader.get().forceParseElement(negativeeMatcher.group(1), lineNumber);

                return create(player, false, lineNumber);
            }

            return null;
        }

        @NotNull
        @Contract(pure = true)
        public PsiHasClientWeatherCondition create(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
            return new PsiHasClientWeatherCondition(player, positive, lineNumber);
        }
    }
}
