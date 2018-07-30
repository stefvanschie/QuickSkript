package com.github.stefvanschie.quickskript.psi.function;

import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.psi.PsiFactory;
import com.github.stefvanschie.quickskript.psi.exception.ParseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Calculates the tangent of a given number
 *
 * @since 0.1.0
 */
public class PsiTangentFunction implements PsiElement<Double> {

    /**
     * The parameter for calculating the tangent
     */
    private PsiElement<Number> parameter;

    /**
     * Creates a new tangent function
     *
     * @param parameter the parameter
     * @since 0.1.0
     */
    private PsiTangentFunction(PsiElement<Number> parameter) {
        this.parameter = parameter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double execute() {
        return Math.tan(parameter.execute().doubleValue());
    }

    /**
     * A factory for creating tangent functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiFactory<PsiTangentFunction> {

        /**
         * The pattern for matching tangent function expressions
         */
        private static final Pattern PATTERN = Pattern.compile("tan\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiTangentFunction parse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<Number> element = (PsiElement<Number>) PsiElementFactory.parseText(expression, Number.class);

            if (element == null)
                throw new ParseException("Function was unable to find an expression named " + expression);

            return new PsiTangentFunction(element);
        }
    }
}
