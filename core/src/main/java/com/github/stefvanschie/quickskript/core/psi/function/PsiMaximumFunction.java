package com.github.stefvanschie.quickskript.core.psi.function;

import com.github.stefvanschie.quickskript.core.psi.util.PsiCollection;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
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
     * @param element an element containing a collection of elements of numbers
     * @param lineNumber the line number
     * @since 0.1.0
     */
    private PsiMaximumFunction(@NotNull PsiElement<?> element, int lineNumber) {
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

        double max = -Double.MAX_VALUE; //negative double max value is the actual smallest value, double min value is still positive

        for (Object object : collection) {
            if (!(object instanceof PsiElement<?>)) {
                throw new ExecutionException("Collection should only contain psi elements, but it didn't", lineNumber);
            }

            double value = ((PsiElement<?>) object).execute(context, Number.class).doubleValue();

            if (max < value) {
                max = value;
            }
        }

        return max;
    }

    /**
     * A factory for creating maximum functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiMaximumFunction> {

        /**
         * The pattern for matching maximum expressions
         */
        @NotNull
        private final Pattern pattern = Pattern.compile("max\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Contract(pure = true)
        @Override
        public PsiMaximumFunction tryParse(@NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches()) {
                return null;
            }

            String[] values = matcher.group(1).replace(" ", "").split(",");

            if (values.length == 1) {
                PsiElement<?> collection = SkriptLoader.get().tryParseElement(values[0], lineNumber);

                if (collection != null) {
                    return new PsiMaximumFunction(collection, lineNumber);
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
        @Contract(pure = true)
        protected PsiMaximumFunction create(@NotNull PsiCollection<?> elements, int lineNumber) {
            return new PsiMaximumFunction(elements, lineNumber);
        }
    }
}
