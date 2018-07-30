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
 * Rounds the provided number up to the nearest integer
 *
 * @since 0.1.0
 */
public class PsiCeilFunction implements PsiElement<Double> {

    /**
     * The parameter to round up
     */
    private PsiElement<Number> parameter;

    /**
     * Creates a ceil function
     *
     * @param parameter the parameter
     * @since 0.1.0
     */
    private PsiCeilFunction(PsiElement<Number> parameter) {
        this.parameter = parameter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double execute() {
        return Math.ceil(parameter.execute().doubleValue());
    }

    /**
     * The factory for creating ceil functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiFactory<PsiCeilFunction> {

        /**
         * The pattern for matching ceil expressions
         */
        private static final Pattern PATTERN = Pattern.compile("ceil(?:ing)?\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiCeilFunction parse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<Number> element = (PsiElement<Number>) PsiElementFactory.parseText(expression, Number.class);

            if (element == null)
                throw new ParseException("Function was unable to find an expression named " + expression);

            return new PsiCeilFunction(element);
        }
    }
}
