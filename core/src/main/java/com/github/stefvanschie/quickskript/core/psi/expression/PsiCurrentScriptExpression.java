package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.skript.Skript;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the current script.
 *
 * @since 0.1.0
 */
public class PsiCurrentScriptExpression extends PsiElement<Skript> {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiCurrentScriptExpression(int lineNumber) {
        super(lineNumber);
    }

    @NotNull
    @Override
    protected Skript executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        if (context == null) {
            throw new ExecutionException("Cannot get script without context", super.lineNumber);
        }

        return context.getSkript();
    }

    /**
     * A factory for creating instances of {@link PsiCurrentScriptExpression}.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("[the] [current] script")
        public PsiCurrentScriptExpression parse(int lineNumber) {
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
        @Contract(value = "_ -> new", pure = true)
        private PsiCurrentScriptExpression create(int lineNumber) {
            return new PsiCurrentScriptExpression(lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.SCRIPT;
        }
    }
}
