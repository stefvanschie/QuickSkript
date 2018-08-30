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
 * Calculates the inverse cosine of the given number
 *
 * @since 0.1.0
 */
public class PsiInverseCosineFunction extends PsiElement<Double> {

    /**
     * The parameter used to calculate the inverse cosine
     */
    private PsiElement<?> parameter;

    /**
     * Creates the inverse cosine
     *
     * @param parameter the parameter for calculating the inverse cosine
     */
    private PsiInverseCosineFunction(PsiElement<?> parameter) {
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

        return Math.acos(((Number) result).doubleValue());
    }

    /**
     * A factory for creating inverse cosine functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiInverseCosineFunction> {

        /**
         * The pattern for matching inverse cosine function expressions
         */
        private final Pattern PATTERN = Pattern.compile("acos\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiInverseCosineFunction tryParse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<?> element = SkriptLoader.get().tryParseElement(expression);

            if (element == null)
                throw new ParseException("Function was unable to find an expression named " + expression);

            return new PsiInverseCosineFunction(element);
        }
    }
}
