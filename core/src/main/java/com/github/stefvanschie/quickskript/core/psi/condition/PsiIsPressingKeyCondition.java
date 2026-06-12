package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks whether the provided players are pressing the provided input keys.
 *
 * @since 0.1.0
 */
public class PsiIsPressingKeyCondition extends PsiElement<Boolean> {

    /**
     * The players to check if they are pressing the input keys.
     */
    @NotNull
    protected final PsiElement<?> players;

    /**
     * The input keys to check if they are being pressed by the players.
     */
    @NotNull
    protected final PsiElement<?> inputKeys;

    /**
     * If false, the result is negated.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param players the players to check if they are pressing the input keys
     * @param inputKeys the input keys to check if they are being pressed by the players
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsPressingKeyCondition(@NotNull PsiElement<?> players, @NotNull PsiElement<?> inputKeys,
                                      boolean positive, int lineNumber) {
        super(lineNumber);

        this.players = players;
        this.inputKeys = inputKeys;
        this.positive = positive;
    }

    /**
     * A factory to create instances of {@link PsiIsPressingKeyCondition}.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param players the players to check if they are pressing the input keys
         * @param inputKeys the input keys to check if they are being pressed
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%players% (is|are|was|were) pressing %input keys%")
        public PsiIsPressingKeyCondition parsePositive(@NotNull PsiElement<?> players, @NotNull PsiElement<?> inputKeys,
                                                       int lineNumber) {
            return create(players, inputKeys, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param players the players to check if they are pressing the input keys
         * @param inputKeys the input keys to check if they are being pressed
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%players% (isn't|is not|aren't|are not|wasn't|was not|weren't|were not) pressing %input keys%")
        public PsiIsPressingKeyCondition parseNegative(@NotNull PsiElement<?> players, @NotNull PsiElement<?> inputKeys,
                                                       int lineNumber) {
            return create(players, inputKeys, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param players the players to check if they are pressing the input keys
         * @param inputKeys the input keys to check if they are being pressed
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(value = "_, _, _, _ -> new", pure = true)
        public PsiIsPressingKeyCondition create(@NotNull PsiElement<?> players, @NotNull PsiElement<?> inputKeys,
                                                boolean positive, int lineNumber) {
            return new PsiIsPressingKeyCondition(players, inputKeys, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public String getType() {
            return "boolean";
        }
    }
}
