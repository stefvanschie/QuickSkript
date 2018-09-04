package com.github.stefvanschie.quickskript.psi.function;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.psi.literal.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.skript.SkriptLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
     * @param element an element containing an iterable of numbers
     * @since 0.1.0
     */
    private PsiMinimumFunction(PsiElement<?> element) {
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
        Iterable<?> iterable = element.execute(context, Iterable.class);

        double min = Double.MAX_VALUE;

        for (Object object : iterable) {
            if (!(object instanceof PsiElement<?>))
                throw new ExecutionException("Iterable should only contain psi elements, but it didn't");

            double value = ((PsiElement<?>) object).execute(context, Number.class).doubleValue();

            if (min > value)
                min = value;
        }

        return min;
    }

    /**
     * A factory for creating minimum functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiMinimumFunction> {

        /**
         * The pattern for matching minimum expressions
         */
        private final Pattern PATTERN = Pattern.compile("min\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiMinimumFunction tryParse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String[] values = matcher.group(1).replace(" ", "").split(",");

            if (values.length == 1) {
                PsiElement<?> iterable = SkriptLoader.get().tryParseElement(values[0]);

                if (iterable != null)
                    return new PsiMinimumFunction(iterable);
            }

            return new PsiMinimumFunction(new PsiPrecomputedHolder<>(Arrays.stream(values)
                    .map(string -> SkriptLoader.get().forceParseElement(string))
                    .collect(Collectors.toList())));
        }
    }
}
