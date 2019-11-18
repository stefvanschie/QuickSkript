package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.expression.util.Resettable;
import com.github.stefvanschie.quickskript.core.psi.expression.util.Settable;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Gets the server icon shown in a ping event
 *
 * @since 0.1.0
 */
public class PsiShownServerIconExpression extends PsiElement<Object> implements Resettable, Settable {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiShownServerIconExpression(int lineNumber) {
        super(lineNumber);
    }

    /**
     * A factory for creating {@link PsiShownServerIconExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiShownServerIconExpression}s
         */
        @NotNull
        private SkriptPattern pattern = SkriptPattern.parse("[the] (shown|sent) [server] icon");

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
        public PsiShownServerIconExpression parse(int lineNumber) {
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
        public PsiShownServerIconExpression create(int lineNumber) {
            return new PsiShownServerIconExpression(lineNumber);
        }
    }
}
