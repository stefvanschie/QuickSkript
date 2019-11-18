package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
     * A factory for creating {@link PsiIsFlyingCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching positive {@link PsiIsOnlineCondition}s
         */
        @NotNull
        private final SkriptPattern positivePattern =
            SkriptPattern.parse("%offline players% (is|are) (online|offline)");

        /**
         * The pattern for matching negative {@link PsiIsOnlineCondition}s
         */
        @NotNull
        private final SkriptPattern negativePattern =
            SkriptPattern.parse("%offline players% (isn't|is not|aren't|are not) (online|offline)");

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param offlinePlayer the offline player to check for
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        public PsiIsOnlineCondition parsePositive(@NotNull PsiElement<?> offlinePlayer, int lineNumber) {
            return create(offlinePlayer, true, lineNumber);
        }

        /**
         * Parses the {@link #negativePattern} and invokes this method with its types if the match succeeds
         *
         * @param offlinePlayer the offline player to check for
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        public PsiIsOnlineCondition parseNegative(@NotNull PsiElement<?> offlinePlayer, int lineNumber) {
            return create(offlinePlayer, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
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
