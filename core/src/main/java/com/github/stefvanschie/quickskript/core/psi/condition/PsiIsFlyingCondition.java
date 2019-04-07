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
 * Checks to see if a player is flying. This cannot be pre computed, since a player's flying state may change during
 * game play.
 *
 * @since 0.1.0
 */
public class PsiIsFlyingCondition extends PsiElement<Boolean> {

    /**
     * The player to check the flying state for
     */
    @NotNull
    protected final PsiElement<?> player;

    /**
     * Is false, the result of execution is inverted
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to check for
     * @param positive if false, the result of execution is inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsFlyingCondition(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
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
     * A factory for creating {@link PsiIsFlyingCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiIsFlyingCondition> {

        /**
         * A pattern for matching positive {@link PsiIsFlyingCondition}s
         */
        @NotNull
        private final Pattern positivePattern = Pattern.compile("([\\s\\S]+) (?:is|are) flying");

        /**
         * A pattern for matching negative {@link PsiIsFlyingCondition}s
         */
        @NotNull
        private final Pattern negativePattern = Pattern.compile("([\\s\\S]+) (?:isn't|is not|aren't|are not) flying");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Contract(pure = true)
        @Override
        public PsiIsFlyingCondition tryParse(@NotNull String text, int lineNumber) {
            Matcher positiveMatcher = positivePattern.matcher(text);

            if (positiveMatcher.matches()) {
                PsiElement<?> player = SkriptLoader.get().forceParseElement(positiveMatcher.group(1), lineNumber);

                return create(player, true, lineNumber);
            }

            Matcher negativeMatcher = negativePattern.matcher(text);

            if (negativeMatcher.matches()) {
                PsiElement<?> player = SkriptLoader.get().forceParseElement(negativeMatcher.group(1), lineNumber);

                return create(player, false, lineNumber);
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param player the player to check for
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiIsFlyingCondition create(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
            return new PsiIsFlyingCondition(player, positive, lineNumber);
        }
    }
}
