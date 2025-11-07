package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Gets the fishing hook from a fishing event.
 *
 * @since 0.1.0
 */
public class PsiFishingHookExpression extends PsiElement<Object> {

    /**
     * Creates a new element with the given line number.
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiFishingHookExpression(int lineNumber) {
        super(lineNumber);
    }

    /**
     * A factory for creating {@link PsiFishingHookExpression}s.
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
        @Contract(value = "_ -> new", pure = true)
        @Pattern("[the] fish[ing] (hook|bobber)")
        public PsiFishingHookExpression parse(int lineNumber) {
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
        public PsiFishingHookExpression create(int lineNumber) {
            return new PsiFishingHookExpression(lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.ENTITY;
        }
    }
}
