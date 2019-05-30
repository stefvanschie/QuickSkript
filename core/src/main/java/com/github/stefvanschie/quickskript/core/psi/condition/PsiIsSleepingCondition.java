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
 * Checks whether a player is currently sleeping. This cannot be pre computed, since the player may enter or leave the
 * bed during game play.
 *
 * @since 0.1.0
 */
public class PsiIsSleepingCondition extends PsiElement<Boolean> {

    /**
     * The player to check whether they are sleeping
     */
    @NotNull
    protected final PsiElement<?> player;

    /**
     * If false, the result of this execution will be negated
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to check whether they are sleeping
     * @param positive if false, the result of this execution will be negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsSleepingCondition(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
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
     * A factory for creating {@link PsiIsSleepingCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiIsSleepingCondition> {

        /**
         * The pattern for matching positive {@link PsiIsSleepingCondition}s
         */
        @NotNull
        private final Pattern positivePattern = Pattern.compile("(?<player>[\\s\\S]+) (?:is|are) sleeping");

        /**
         * The pattern for matching negative {@link PsiIsSleepingCondition}s
         */
        @NotNull
        private final Pattern negativePattern =
            Pattern.compile("(?<player>[\\s\\S]+) (?:isn't|is not|aren't|are not) sleeping");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Contract(pure = true)
        @Override
        public PsiIsSleepingCondition tryParse(@NotNull String text, int lineNumber) {
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
         * @param player the player to check whether they are sleeping
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiIsSleepingCondition create(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
            return new PsiIsSleepingCondition(player, positive, lineNumber);
        }
    }
}
