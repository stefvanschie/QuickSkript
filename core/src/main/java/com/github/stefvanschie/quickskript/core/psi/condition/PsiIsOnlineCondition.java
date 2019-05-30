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
 * Checks whether a player is online. This cannot be pre computed, since players can log in/log out during game play.
 *
 * @since 0.1.0
 */
public class PsiIsOnlineCondition extends PsiElement<Boolean> {

    /**
     * The offline player to check whether they are online
     */
    @NotNull
    protected final PsiElement<?> offlinePlayer;

    /**
     * False if the result of the execution should be negated
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param offlinePlayer the offline player to check whether they are online
     * @param positive false if the result of the execution should be negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsOnlineCondition(@NotNull PsiElement<?> offlinePlayer, boolean positive, int lineNumber) {
        super(lineNumber);

        this.offlinePlayer = offlinePlayer;
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
     * A factory for creating {@link PsiIsFlyingCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiIsOnlineCondition> {

        /**
         * The pattern for matching positive {@link PsiIsOnlineCondition}s
         */
        @NotNull
        private final Pattern positivePattern =
            Pattern.compile("(?<offlinePlayer>[\\s\\S]+) (?:(?:is|are) online|(?:isn't|is not|aren't|are not) offline)");

        /**
         * The pattern for matching negative {@link PsiIsOnlineCondition}s
         */
        @NotNull
        private final Pattern negativePattern =
            Pattern.compile("(?<offlinePlayer>[\\s\\S]+) (?:(?:is|are) offline|(?:isn't|is not|aren't|are not) online)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Contract(pure = true)
        @Override
        public PsiIsOnlineCondition tryParse(@NotNull String text, int lineNumber) {
            var skriptLoader = SkriptLoader.get();

            Matcher positiveMatcher = positivePattern.matcher(text);

            if (positiveMatcher.matches()) {
                String offlinePlayerGroup = positiveMatcher.group("offlinePlayer");

                PsiElement<?> offlinePlayer = skriptLoader.forceParseElement(offlinePlayerGroup, lineNumber);

                return create(offlinePlayer, true, lineNumber);
            }

            Matcher negativeMatcher = negativePattern.matcher(text);

            if (negativeMatcher.matches()) {
                String offlinePlayerGroup = negativeMatcher.group("offlinePlayer");

                PsiElement<?> offlinePlayer = skriptLoader.forceParseElement(offlinePlayerGroup, lineNumber);

                return create(offlinePlayer, false, lineNumber);
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param offlinePlayer the offline player to check for
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiIsOnlineCondition create(@NotNull PsiElement<?> offlinePlayer, boolean positive, int lineNumber) {
            return new PsiIsOnlineCondition(offlinePlayer, positive, lineNumber);
        }
    }
}
