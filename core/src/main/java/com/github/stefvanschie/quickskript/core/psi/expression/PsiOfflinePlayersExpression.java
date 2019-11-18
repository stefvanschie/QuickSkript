package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Gets an array of all players that have ever joined this server. This includes both players that are currently online
 * and players that are currently offline.
 *
 * @since 0.1.0
 */
public class PsiOfflinePlayersExpression extends PsiElement<Object[]> {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiOfflinePlayersExpression(int lineNumber) {
        super(lineNumber);
    }

    /**
     * A factory for creating {@link PsiOfflinePlayersExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiOfflinePlayersExpression}s
         */
        @NotNull
        private SkriptPattern pattern = SkriptPattern.parse("[all [[of] the]|the] offline[ ]players");

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiOfflinePlayersExpression parse(int lineNumber) {
            return create(lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiOfflinePlayersExpression create(int lineNumber) {
            return new PsiOfflinePlayersExpression(lineNumber);
        }
    }
}
