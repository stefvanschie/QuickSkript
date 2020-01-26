package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.util.literal.GameMode;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets a game mode
 *
 * @since 0.1.0
 */
public class PsiGameModeLiteral extends PsiPrecomputedHolder<GameMode> {

    /**
     * Creates a new element with the given line number
     *
     * @param gameMode the game mode
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiGameModeLiteral(@NotNull GameMode gameMode, int lineNumber) {
        super(gameMode, lineNumber);
    }

    /**
     * A factory for creating {@link PsiGameModeLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * This gets called upon parsing
         *
         * @param text the text to parse
         * @param lineNumber the line number
         * @return the literal, or null to indicate failure
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiGameModeLiteral parse(@NotNull String text, int lineNumber) {
            for (GameMode gameMode : GameMode.values()) {
                if (gameMode.name().replace('_', ' ').equalsIgnoreCase(text)) {
                    return create(gameMode, lineNumber);
                }
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param gameMode the game mode
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiGameModeLiteral create(@NotNull GameMode gameMode, int lineNumber) {
            return new PsiGameModeLiteral(gameMode, lineNumber);
        }
    }
}
