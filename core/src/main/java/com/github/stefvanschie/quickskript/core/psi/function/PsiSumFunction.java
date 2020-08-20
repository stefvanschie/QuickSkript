package com.github.stefvanschie.quickskript.core.psi.function;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.util.PsiCollection;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.Contract;
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
     * @param lineNumber the line number
     * @since 0.1.0
     */
    private PsiSumFunction(@NotNull PsiElement<?> element, int lineNumber) {
        super(lineNumber);

        this.element = element;

        if (this.element.isPreComputed()) {
            preComputed = executeImpl(null);
            this.element = null;
        }
    }

    @NotNull
    @Override
    protected Double executeImpl(@Nullable Context context) {
        Object object = element.execute(context);

        double sum = 0;
        Collection<Object> objects = PsiCollection.toCollection(object);

        if (objects == null) {
            throw new ExecutionException("Element was not a collection or array", lineNumber);
        }

        for (Object obj : objects) {
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
         * The pattern for matching sum expressions
         */
        @NotNull
        private final Pattern pattern = Pattern.compile("sum\\((?<parameters>[\\s\\S]+)\\)");

        /**
         * This gets called upon parsing
         *
         * @param text the text to parse
         * @param lineNumber the line number
         * @return the function, or null to indicate failure
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiSumFunction tryParse(@NotNull SkriptLoader skriptLoader, @NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches()) {
                return null;
            }

            String[] values = matcher.group("parameters").replace(" ", "").split(",");

            if (values.length == 1) {
                PsiElement<?> collection = skriptLoader.tryParseElement(values[0], lineNumber);

                if (collection != null) {
                    return create(collection, lineNumber);
                }
            }
            
            return create(new PsiCollection<>(Arrays.stream(values)
                .map(string -> skriptLoader.forceParseElement(string, lineNumber)), lineNumber), lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the
         * {@link #tryParse(SkriptLoader, String, int)} method.
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
    }
}
