package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if the player has chat filtering enabled.
 *
 * @since 0.1.0
 */
public class PsiHasChatFilteringCondition extends PsiElement<Boolean> {

    /**
     * The players to check if they have chat filtering enabled.
     */
    @NotNull
    protected final PsiElement<?> players;

    /**
     * If false, the result is negated.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param players the players to check if they have chat filtering enabled
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiHasChatFilteringCondition(@NotNull PsiElement<?> players, boolean positive, int lineNumber) {
        super(lineNumber);

        this.players = players;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiHasChatFilteringCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching positive {@link PsiHasChatFilteringCondition}s
         */
        @NotNull
        private final SkriptPattern positivePattern = SkriptPattern.parse(
            "%players% (has|have) (chat|text) filtering (on|enabled)"
        );

        /**
         * The pattern for matching negative {@link PsiHasChatFilteringCondition}s
         */
        @NotNull
        private final SkriptPattern negativePattern = SkriptPattern.parse(
            "%players% (doesn't|does not|do not|don't) have (chat|text) filtering (on|enabled)"
        );

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param players the players to check if they have chat filtering enabled
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        public PsiHasChatFilteringCondition parsePositive(@NotNull PsiElement<?> players, int lineNumber) {
            return create(players, true, lineNumber);
        }

        /**
         * Parses the {@link #negativePattern} and invokes this method with its types if the match succeeds
         *
         * @param players the players to check if they don't have chat filtering enabled
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        public PsiHasChatFilteringCondition parseNegative(@NotNull PsiElement<?> players, int lineNumber) {
            return create(players, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param players the players to check if they have chat filtering enabled
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiHasChatFilteringCondition create(@NotNull PsiElement<?> players, boolean positive, int lineNumber) {
            return new PsiHasChatFilteringCondition(players, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
