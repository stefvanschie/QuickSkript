package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Gets who ran the execute command.
 *
 * @see com.github.stefvanschie.quickskript.core.context.ExecuteContext
 * @since 0.1.0
 */
public class PsiMeExpression extends PsiElement<Object> {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiMeExpression(int lineNumber) {
        super(lineNumber);
    }

    /**
     * A factory for creating {@link PsiMeExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The patterns for parsing {@link PsiMeExpression}s
         */
        @NotNull
        private SkriptPattern[] patterns = SkriptPattern.parse("me", "my[self]");

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
        public PsiMeExpression parse(int lineNumber) {
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
        public PsiMeExpression create(int lineNumber) {
            return new PsiMeExpression(lineNumber);
        }
    }
}
