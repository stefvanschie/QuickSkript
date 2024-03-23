package com.github.stefvanschie.quickskript.core.psi.function;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.util.MultiResult;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Returns the lowest value from a given collection of numbers
 *
 * @since 0.1.0
 */
public class PsiMinimumFunction extends PsiElement<Double> {

    /**
     * An element containing a bunch of numbers
     */
    private PsiElement<?> element;

    /**
     * Creates a new minimum function
     *
     * @param element an element containing a collection of elements of numbers
     * @param lineNumber the line number
     * @since 0.1.0
     */
    private PsiMinimumFunction(@NotNull PsiElement<?> element, int lineNumber) {
        super(lineNumber);

        this.element = element;

        if (this.element.isPreComputed()) {
            preComputed = executeImpl(null, null);
            this.element = null;
        }
    }

    @NotNull
    @Override
    protected Double executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        MultiResult<?> object = element.executeMulti(environment, context);

        return object.stream().mapToDouble(e -> ((Number) e).doubleValue())
                .min()
                .orElseThrow(() -> new ExecutionException("The collection or array was empty", lineNumber));
    }

    /**
     * A factory for creating minimum functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching minimum expressions
         */
        @NotNull
        private final SkriptPattern pattern = SkriptPattern.parse("min\\(%numbers%\\)");

        /**
         * This gets called upon parsing
         *
         * @param numbers the numbers
         * @param lineNumber the line number
         * @return the function, or null to indicate failure
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiMinimumFunction tryParse(@NotNull PsiElement<?> numbers, int lineNumber) {
            return create(numbers, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(PsiElement, int)}
         * method.
         *
         * @param elements the elements to compute
         * @param lineNumber the line number
         * @return the function
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        protected PsiMinimumFunction create(@NotNull PsiElement<?> elements, int lineNumber) {
            return new PsiMinimumFunction(elements, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.NUMBER;
        }
    }
}
