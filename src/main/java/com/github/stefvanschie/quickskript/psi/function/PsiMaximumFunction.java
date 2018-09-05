package com.github.stefvanschie.quickskript.psi.function;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.psi.literal.PsiCollection;
import com.github.stefvanschie.quickskript.skript.SkriptLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        Collection<?> collection = element.execute(context, Collection.class);

        double max = -Double.MAX_VALUE; //negative double max value is the actual smallest value, double min value is still positive

        for (Object object : collection) {
            if (!(object instanceof PsiElement<?>))
                throw new ExecutionException("Collection should only contain psi elements, but it didn't");

            double value = ((PsiElement<?>) object).execute(context, Number.class).doubleValue();

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
    public static class Factory implements PsiElementFactory<PsiMaximumFunction> {

        /**
         * The pattern for matching maximum expressions
         */
        private final Pattern pattern = Pattern.compile("max\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiMaximumFunction tryParse(@NotNull String text) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches())
                return null;

            String[] values = matcher.group(1).replace(" ", "").split(",");

            if (values.length == 1) {
                PsiElement<?> collection = SkriptLoader.get().tryParseElement(values[0]);

                if (collection != null)
                    return new PsiMaximumFunction(collection);
            }

            return new PsiMaximumFunction(new PsiCollection<>(Arrays.stream(values)
                    .map(string -> SkriptLoader.get().forceParseElement(string))
                    .collect(Collectors.toList())));
        }
    }
}
