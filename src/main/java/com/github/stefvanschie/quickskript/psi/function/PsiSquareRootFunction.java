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
 * Calculates the square root of the given number
 *
 * @since 0.1.0
 */
public class PsiSquareRootFunction extends PsiElement<Double> {

    /**
     * The parameter to use for calculating the square root
     */
    private PsiElement<Number> parameter;

    /**
     * Creates a new square root function
     *
     * @param parameter the parameter
     * @since 0.1.0
     */
    private PsiSquareRootFunction(PsiElement<Number> parameter) {
        this.parameter = parameter;

        if (this.parameter.isPreComputed()) {
            preComputed = executeImpl();
            this.parameter = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected Double executeImpl() {
        return Math.sqrt(parameter.execute().doubleValue());
    }

    /**
     * A factory for creating square root functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiFactory<PsiSquareRootFunction> {

        /**
         * The pattern for matching square root function expressions
         */
        private static final Pattern PATTERN = Pattern.compile("sqrt\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiSquareRootFunction parse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<Number> element = (PsiElement<Number>) PsiElementFactory.parseText(expression, Number.class);

            if (element == null)
                throw new ParseException("Function was unable to find an expression named " + expression);

            return new PsiSquareRootFunction(element);
        }
    }
}
