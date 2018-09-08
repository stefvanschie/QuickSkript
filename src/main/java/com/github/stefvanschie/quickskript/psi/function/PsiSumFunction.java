package com.github.stefvanschie.quickskript.psi.function;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.psi.util.PsiCollection;
import com.github.stefvanschie.quickskript.skript.SkriptLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * @since 0.1.0
     */
    private PsiSumFunction(PsiElement<?> element) {
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

        double result = 0;

        for (Object object : collection) {
            if (!(object instanceof PsiElement<?>))
                throw new ExecutionException("Collection should only contain psi elements, but it didn't");

            result += ((PsiElement<?>) object).execute(context, Number.class).doubleValue();
        }

        return result;
    }

    /**
     * A factory for creating sum functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiSumFunction> {

        /**
         * The pattern for matching sum expressions
         */
        private final Pattern pattern = Pattern.compile("sum\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiSumFunction tryParse(@NotNull String text) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches())
                return null;

            String[] values = matcher.group(1).replace(" ", "").split(",");

            if (values.length == 1) {
                PsiElement<?> collection = SkriptLoader.get().tryParseElement(values[0]);

                if (collection != null)
                    return new PsiSumFunction(collection);
            }
            
            return new PsiSumFunction(new PsiCollection<>(Arrays.stream(values)
                    .map(string -> SkriptLoader.get().forceParseElement(string))));
        }
    }
}
