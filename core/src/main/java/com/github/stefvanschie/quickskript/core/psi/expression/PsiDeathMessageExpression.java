package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.expression.util.Settable;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Gets the death message of a death event
 *
 * @since 0.1.0
 */
public class PsiDeathMessageExpression extends PsiElement<Text> implements Settable {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiDeathMessageExpression(int lineNumber) {
        super(lineNumber);
    }

    /**
     * A factory for creating {@link PsiDeathMessageExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * A pattern for matching {@link PsiDeathMessageExpression}s
         */
        @NotNull
        private final SkriptPattern pattern = SkriptPattern.parse("[the] death( |-)message");

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
        public PsiDeathMessageExpression parse(int lineNumber) {
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
        public PsiDeathMessageExpression create(int lineNumber) {
            return new PsiDeathMessageExpression(lineNumber);
        }
    }
}
