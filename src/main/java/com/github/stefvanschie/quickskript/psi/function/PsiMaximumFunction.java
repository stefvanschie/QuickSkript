package com.github.stefvanschie.quickskript.psi.function;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.psi.PsiFactory;
import com.github.stefvanschie.quickskript.psi.exception.ExecutionException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Returns the highest value from a given collection of numbers
 *
 * @since 0.1.0
 */
public class PsiMaximumFunction extends PsiElement<Double> {

    /**
     * An element containing a bunch of numbers.
     */
    private PsiElement<?> element;

    /**
     * Creates a new maximum function
     *
     * @param element an element containing an iterable of numbers
     * @since 0.1.0
     */
    private PsiMaximumFunction(PsiElement<?> element) {
        this.element = element;

        if (this.element.isPreComputed()) {
            preComputed = executeImpl(null);
            this.element = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected Double executeImpl(@Nullable Context context) {
        Object elementResult = element.execute(context);

        if (!(elementResult instanceof Iterable<?>))
            throw new ExecutionException("Result of expression should be an iterable, but it wasn't");

        Iterable<?> iterable = (Iterable<?>) elementResult;

        double max = -Double.MAX_VALUE; //negative double max value is the actual smallest value, double min value is still positive

        for (Object object : iterable) {
            if (!(object instanceof PsiElement<?>))
                throw new ExecutionException("Iterable should only contain psi elements, but it didn't");

            Object numberResult = ((PsiElement) object).execute(context);

            if (!(numberResult instanceof Number))
                throw new ExecutionException("Result of expression should be a number, but it wasn't");

            double value = ((Number) numberResult).doubleValue();

            if (max < value)
                max = value;
        }

        return max;
    }

    /**
     * A factory for creating maximum functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiFactory<PsiMaximumFunction> {

        /**
         * The pattern for matching maximum expressions
         */
        private static final Pattern PATTERN = Pattern.compile("max\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiMaximumFunction parse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String[] values = matcher.group(1).replace(" ", "").split(",");

            if (values.length == 1) {
                PsiElement<?> iterable = PsiElementFactory.parseText(values[0]);

                if (iterable != null)
                    return new PsiMaximumFunction(iterable);
            }

            Set<PsiElement<?>> numbers = new HashSet<>();

            for (String value : values)
                numbers.add(PsiElementFactory.parseText(value));

            PsiElement<Iterable<?>> iterable = new PsiElement<Iterable<?>>() {
                {
                    preComputed = numbers;
                }

                @Override
                protected Iterable<?> executeImpl(@Nullable Context context) {
                    throw new AssertionError("Since this preComputed variable is always set, this method should never get called");
                }
            };

            return new PsiMaximumFunction(iterable);
        }
    }
}
