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
 * Calculates the cosine of the given number
 *
 * @since 0.1.0
 */
public class PsiCosineFunction implements PsiElement<Double> {

    /**
     * The parameter for calculating the cosine
     */
    private PsiElement<Number> parameter;

    /**
     * Creates a cosine function
     *
     * @param parameter the parameter 0.1.0
     */
    private PsiCosineFunction(PsiElement<Number> parameter) {
        this.parameter = parameter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double execute() {
        return Math.cos(parameter.execute().doubleValue());
    }

    /**
     * A factory for creating cosine functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiFactory<PsiCosineFunction> {

        /**
         * The pattern for matching cosine expressions
         */
        private static final Pattern PATTERN = Pattern.compile("cos\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiCosineFunction parse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<Number> element = (PsiElement<Number>) PsiElementFactory.parseText(expression, Number.class);

            if (element == null)
                throw new ParseException("Function was unable to find an expression named " + expression);

            return new PsiCosineFunction(element);
        }
    }

    static {
        PsiElementFactory.getClassTypes().put(PsiCosineFunction.class, Double.class);
    }
}
