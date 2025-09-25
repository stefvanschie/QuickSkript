package com.github.stefvanschie.quickskript.core.psi.function;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
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
 * Returns the sum of a given collection of numbers
 *
 * @since 0.1.0
 */
public class PsiSumFunction extends PsiElement<Double> {

    /**
     * An element containing a bunch of numbers
     */
    private PsiElement<?> element;

    /**
     * Creates a new sum function
     *
     * @param element an element containing a collection of elements of numbers
     * @param lineNumber the line number
     * @since 0.1.0
     */
    private PsiSumFunction(@NotNull PsiElement<?> element, int lineNumber) {
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
        MultiResult<?> objects = element.executeMulti(environment, context);

        double sum = 0;

        for (Object obj : objects) {
            if (!(obj instanceof Number)) {
                throw new ExecutionException("Result of " + getClass().getSimpleName() +
                    " should be MultiResult<? extends Number>, but it was " +
                    (obj == null ? "null" : obj.getClass().getSimpleName()), lineNumber);
            }

            sum += ((Number) obj).doubleValue();
        }

        return sum;
    }

    /**
     * A factory for creating sum functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * This gets called upon parsing
         *
         * @param numbers the numbers to sum
         * @param lineNumber the line number
         * @return the function, or null to indicate failure
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Pattern("sum\\(%numbers%\\)")
        public PsiSumFunction tryParse(@NotNull PsiElement<?> numbers, int lineNumber) {
            return create(numbers, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param elements the elements to compute
         * @param lineNumber the line number
         * @return the function
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        protected PsiSumFunction create(@NotNull PsiElement<?> elements, int lineNumber) {
            return new PsiSumFunction(elements, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.NUMBER;
        }
    }
}
