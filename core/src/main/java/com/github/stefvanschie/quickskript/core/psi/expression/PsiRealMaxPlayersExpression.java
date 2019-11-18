package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Gets the real amount of maximum players that can play on this server at once. This cannot be pre-computed, since this
 * may change during gameplay.
 *
 * @since 0.1.0
 */
public class PsiRealMaxPlayersExpression extends PsiElement<Integer> {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiRealMaxPlayersExpression(int lineNumber) {
        super(lineNumber);
    }

    /**
     * A factory for creating {@link PsiRealMaxPlayersExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The patterns for matching {@link PsiRealMaxPlayersExpression}s
         */
        @NotNull
        private final SkriptPattern[] patterns = SkriptPattern.parse(
            "[the] [real|default] max[imum] player[s] [count|amount|number|size]",
            "[the] [real|default] max[imum] (count|amount|number|size) of players"
        );

        /**
         * Parses the {@link #patterns} and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patterns")
        public PsiRealMaxPlayersExpression parse(int lineNumber) {
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
        public PsiRealMaxPlayersExpression create(int lineNumber) {
            return new PsiRealMaxPlayersExpression(lineNumber);
        }
    }
}
