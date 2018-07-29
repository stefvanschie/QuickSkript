package com.github.stefvanschie.quickskript.psi.function;

import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.psi.PsiFactory;
import com.github.stefvanschie.quickskript.psi.exception.ParseException;
import com.github.stefvanschie.quickskript.psi.util.ConversionUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Returns the lowest value from a given collection of numbers
 *
 * @since 0.1.0
 */
public class PsiMinimumFunction implements PsiElement<Double> {

    /**
     * The collection of numbers. Only in use when element isn't in use.
     */
    private Collection<PsiElement<Number>> numbers;

    /**
     * An element containing a bunch of numbers. Only in use when numbers isn't in use.
     */
    private PsiElement<Iterable<Number>> element;

    /**
     * Creates a new minimum function
     *
     * @param numbers a collection of numbers
     * @since 0.1.0
     */
    private PsiMinimumFunction(Collection<PsiElement<Number>> numbers) {
        this.numbers = numbers;
    }

    /**
     * Creates a new minimum function
     *
     * @param element an element containing an iterable of numbers
     * @since 0.1.0
     */
    private PsiMinimumFunction(PsiElement<Iterable<Number>> element) {
        this.element = element;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double execute() {
        Stream<Number> stream = null;

        if (numbers == null && element != null)
            stream = StreamSupport.stream(element.execute().spliterator(), false);
        else if (numbers != null && element == null)
            stream = numbers.stream().map(PsiElement::execute);

        if (stream == null)
            throw new IllegalStateException("Neither numbers or element is initialized; unable to compute value");

        return stream.mapToDouble(Number::doubleValue).min().orElse(0);
    }

    /**
     * A factory for creating minimum functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiFactory<PsiMinimumFunction> {

        /**
         * The pattern for matching minimum expressions
         */
        private static final Pattern PATTERN = Pattern.compile("min\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiMinimumFunction parse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String[] values = matcher.group(1).replace(" ", "").split(",");

            if (values.length == 1) {
                PsiElement<Iterable<Number>> iterable =
                    (PsiElement<Iterable<Number>>) PsiElementFactory.parseText(values[0], Iterable.class);

                if (iterable != null)
                    return new PsiMinimumFunction(iterable);
            }

            Collection<PsiElement<Number>> numbers = new HashSet<>(values.length);

            ConversionUtil.convert(values, numbers);

            return new PsiMinimumFunction(numbers);
        }
    }

    static {
        PsiElementFactory.getClassTypes().put(PsiMinimumFunction.class, Double.class);
    }
}
