package com.github.stefvanschie.quickskript.psi.function;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.psi.exception.ParseException;
import com.github.stefvanschie.quickskript.skript.SkriptLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Calculates the natural logarithm of the given number
 *
 * @since 0.1.0
 */
public class PsiNaturalLogarithmFunction extends PsiElement<Double> {

    /**
     * The parameter for calculating the given number
     */
    private PsiElement<?> parameter;

    /**
     * Creates a new natural logarithm function
     *
     * @param parameter the parameter
     * @since 0.1.0
     */
    private PsiNaturalLogarithmFunction(PsiElement<?> parameter) {
        this.parameter = parameter;

        if (this.parameter.isPreComputed()) {
            preComputed = executeImpl(null);
            this.parameter = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected Double executeImpl(@Nullable Context context) {
        Object result = parameter.execute(context);

        if (!(result instanceof Number))
            throw new ExecutionException("Result of expression should be a number, but it wasn't");

        return Math.log(((Number) result).doubleValue());
    }

    /**
     * A factory for creating natural logarithm functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiNaturalLogarithmFunction> {

        /**
         * The pattern for matching cosine expressions
         */
        private final Pattern PATTERN = Pattern.compile("ln\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiNaturalLogarithmFunction tryParse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<?> element = SkriptLoader.get().tryParseElement(expression);

            if (element == null)
                throw new ParseException("Function was unable to find an expression named " + expression);

            return new PsiNaturalLogarithmFunction(element);
        }
    }
}
