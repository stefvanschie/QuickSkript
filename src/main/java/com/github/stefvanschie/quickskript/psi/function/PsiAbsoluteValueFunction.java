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
 * Calculates the absolute value of a given number
 *
 * @since 0.1.0
 */
public class PsiAbsoluteValueFunction implements PsiElement<Double> {

    /**
     * The parameter given for this element
     */
    private PsiElement<Number> parameter;

    /**
     * Creates the absolute value function
     *
     * @param parameter the parameter for calculating this value
     */
    private PsiAbsoluteValueFunction(PsiElement<Number> parameter) {
        this.parameter = parameter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double execute() {
        return Math.abs(parameter.execute().doubleValue());
    }

    /**
     * The factory for creating {@link PsiAbsoluteValueFunction}s.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiFactory<PsiAbsoluteValueFunction> {

        /**
         * The pattern for matching absolute value functions
         */
        private static final Pattern PATTERN = Pattern.compile("abs\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiAbsoluteValueFunction parse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<Number> element = (PsiElement<Number>) PsiElementFactory.parseText(expression, Number.class);

            if (element == null)
                throw new ParseException("Function was unable to find an expression named " + expression);

            return new PsiAbsoluteValueFunction(element);
        }
    }
}
