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
 * Calculates the inverse cosine of the given number
 *
 * @since 0.1.0
 */
public class PsiInverseCosineFunction implements PsiElement<Double> {

    /**
     * The parameter used to calculate the inverse cosine
     */
    private PsiElement<Number> parameter;

    /**
     * Creates the inverse cosine
     *
     * @param parameter the parameter for calculating the inverse cosine
     */
    private PsiInverseCosineFunction(PsiElement<Number> parameter) {
        this.parameter = parameter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double execute() {
        return Math.acos(parameter.execute().doubleValue());
    }

    /**
     * A factory for creating inverse cosine functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiFactory<PsiInverseCosineFunction> {

        /**
         * The pattern for matching inverse cosine function expressions
         */
        private static final Pattern PATTERN = Pattern.compile("acos\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiInverseCosineFunction parse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<Number> element = (PsiElement<Number>) PsiElementFactory.parseText(expression, Number.class);

            if (element == null)
                throw new ParseException("Function was unable to find an expression named " + expression);

            return new PsiInverseCosineFunction(element);
        }
    }

    static {
        PsiElementFactory.getClassTypes().put(PsiInverseCosineFunction.class, Double.class);
    }
}
