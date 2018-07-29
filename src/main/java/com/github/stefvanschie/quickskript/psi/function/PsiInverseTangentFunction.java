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
 * Calculates the inverse tangent of a given number
 *
 * @since 0.1.0
 */
public class PsiInverseTangentFunction implements PsiElement<Double> {

    /**
     * The parameter for calculating the inverse tangent
     */
    private PsiElement<Number> parameter;

    /**
     * Creates a new inverse tangent function
     *
     * @param parameter the parameter
     * @since 0.1.0
     */
    private PsiInverseTangentFunction(PsiElement<Number> parameter) {
        this.parameter = parameter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double execute() {
        return Math.atan(parameter.execute().doubleValue());
    }

    /**
     * A factory for creating inverse tangent functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiFactory<PsiInverseTangentFunction> {

        /**
         * The pattern for matching inverse tangent function expressions
         */
        private static final Pattern PATTERN = Pattern.compile("atan\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiInverseTangentFunction parse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<Number> element = (PsiElement<Number>) PsiElementFactory.parseText(expression, Number.class);

            if (element == null)
                throw new ParseException("Function was unable to find an expression named " + expression);

            return new PsiInverseTangentFunction(element);
        }
    }

    static {
        PsiElementFactory.getClassTypes().put(PsiInverseTangentFunction.class, Double.class);
    }
}
