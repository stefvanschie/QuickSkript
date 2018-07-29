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
 * Calculates the inverse sine of the given number
 *
 * @since 0.1.0
 */
public class PsiInverseSineFunction implements PsiElement<Double> {

    /**
     * The parameter to use for calculating the inverse sine
     */
    private PsiElement<Number> parameter;

    /**
     * Creates a new inverse sine function
     *
     * @param parameter the parameter
     * @since 0.1.0
     */
    private PsiInverseSineFunction(PsiElement<Number> parameter) {
        this.parameter = parameter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double execute() {
        return Math.asin(parameter.execute().doubleValue());
    }

    /**
     * A factory for creating inverse sine functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiFactory<PsiInverseSineFunction> {

        /**
         * The pattern for matching inverse sine function expressions
         */
        private static final Pattern PATTERN = Pattern.compile("asin\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiInverseSineFunction parse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<Number> element = (PsiElement<Number>) PsiElementFactory.parseText(expression, Number.class);

            if (element == null)
                throw new ParseException("Function was unable to find an expression named " + expression);

            return new PsiInverseSineFunction(element);
        }
    }

    static {
        PsiElementFactory.getClassTypes().put(PsiInverseSineFunction.class, Double.class);
    }
}
