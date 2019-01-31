package com.github.stefvanschie.quickskript.core.psi.function;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.util.PsiCollection;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    protected PsiMinimumFunction(PsiElement<?> element, int lineNumber) {
        super(lineNumber);

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
        Collection<?> collection = element.execute(context, Collection.class);

        double min = Double.MAX_VALUE;

        for (Object object : collection) {
            if (!(object instanceof PsiElement<?>)) {
                throw new ExecutionException("Collection should only contain psi elements, but it didn't", lineNumber);
            }

            double value = ((PsiElement<?>) object).execute(context, Number.class).doubleValue();

            if (min > value) {
                min = value;
            }
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
        private final Pattern pattern = Pattern.compile("min\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiMinimumFunction tryParse(@NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches()) {
                return null;
            }

            String[] values = matcher.group(1).replace(" ", "").split(",");

            if (values.length == 1) {
                PsiElement<?> collection = SkriptLoader.get().tryParseElement(values[0], lineNumber);

                if (collection != null) {
                    return create(collection, lineNumber);
                }
            }

            return create(new PsiCollection<>(Arrays.stream(values)
                .map(string -> SkriptLoader.get().forceParseElement(string, lineNumber)), lineNumber), lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param elements the elements to compute
         * @param lineNumber the line number
         * @return the function
         * @since 0.1.0
         */
        @NotNull
        protected PsiMinimumFunction create(PsiElement<?> elements, int lineNumber) {
            return new PsiMinimumFunction(elements, lineNumber);
        }
    }
}