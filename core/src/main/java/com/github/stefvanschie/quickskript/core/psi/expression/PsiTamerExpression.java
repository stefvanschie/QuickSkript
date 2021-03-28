package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Gets the tamer from a tame event
 *
 * @since 0.1.0
 */
public class PsiTamerExpression extends PsiElement<Object> {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiTamerExpression(int lineNumber) {
        super(lineNumber);
    }

    /**
     * A factory for creating {@link PsiTamerExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiTamerExpression}s
         */
        @NotNull
        private SkriptPattern pattern = SkriptPattern.parse("[the] tamer");

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
        public PsiTamerExpression parse(int lineNumber) {
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
        public PsiTamerExpression create(int lineNumber) {
            return new PsiTamerExpression(lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.OBJECT;
        }
    }
}
