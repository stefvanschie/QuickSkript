package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An input for a {@link PsiFilterExpression}. Can only be used in a predicate for a {@link PsiFilterExpression}.
 *
 * @since 0.1.0
 */
//TODO make type do something
public class PsiFilterInputExpression extends PsiElement<Object> {

    /**
     * The type of the filter input
     */
    private final PsiElement<?> type;

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiFilterInputExpression(PsiElement<?> type, int lineNumber) {
        super(lineNumber);

        this.type = type;
    }

    @Nullable
    @Override
    protected Object executeImpl(@Nullable Context context) {
        PsiElement<?> element = this;

        do {
            element = element.getParent();
        } while (element != null && !(element instanceof PsiFilterExpression));

        if (element == null) {
            throw new ExecutionException("Filter input is not inside a filter expression", lineNumber);
        }

        return ((PsiFilterExpression) element).getCurrentLoopingElement();
    }

    /**
     * A factory for creating {@link PsiFilterInputExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiFilterInputExpression}s
         */
        @NotNull
        private final SkriptPattern pattern = SkriptPattern.parse("[%*type%] input");

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param type the type of the input
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiFilterInputExpression parse(@Nullable PsiElement<?> type, int lineNumber) {
            return create(type, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param type the type of the input
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiFilterInputExpression create(@Nullable PsiElement<?> type, int lineNumber) {
            return new PsiFilterInputExpression(type, lineNumber);
        }
    }
}
