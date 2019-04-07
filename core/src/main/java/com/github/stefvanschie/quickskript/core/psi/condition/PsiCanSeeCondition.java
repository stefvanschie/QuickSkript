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
    protected boolean positive;

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
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Nullable
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        throw new UnsupportedOperationException("Cannot execute expression without implementation.");
    }

    /**
     * A factory for creating {@link PsiCanSeeCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiCanSeeCondition> {

        /**
         * A pattern for matching positive can see conditions
         */
        @NotNull
        private final Pattern positivePattern = Pattern
            .compile("([\\s\\S]+) (?:(?:is|are) visible for|(?:is|are)(?:n't| not) invisible for|can see) ([\\s\\S]+)");

        /**
         * A pattern for matching negative can see conditions
         */
        @NotNull
        private final Pattern negativePattern = Pattern.compile(
            "([\\s\\S]+) (?:(?:is|are) invisible for|(?:is|are)(?:n't| not) visible for|can(?:'t| not) see) ([\\s\\S]+)"
        );

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Contract(pure = true)
        @Override
        public PsiCanSeeCondition tryParse(@NotNull String text, int lineNumber) {
            Matcher positiveMatcher = positivePattern.matcher(text);

            if (positiveMatcher.matches()) {
                PsiElement<?> player = SkriptLoader.get().forceParseElement(positiveMatcher.group(1), lineNumber);
                PsiElement<?> targetPlayer = SkriptLoader.get().forceParseElement(positiveMatcher.group(2), lineNumber);

                return create(player, targetPlayer, true, lineNumber);
            }

            Matcher negativeMatcher = negativePattern.matcher(text);

            if (negativeMatcher.matches()) {
                PsiElement<?> player = SkriptLoader.get().forceParseElement(negativeMatcher.group(1), lineNumber);
                PsiElement<?> targetPlayer = SkriptLoader.get().forceParseElement(negativeMatcher.group(2), lineNumber);

                return create(player, targetPlayer, false, lineNumber);
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
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
