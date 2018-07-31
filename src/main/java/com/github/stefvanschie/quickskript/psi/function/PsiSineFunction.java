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
 * Calculates the sine of the given number
 *
 * @since 0.1.0
 */
public class PsiSineFunction extends PsiElement<Double> {

    /**
     * The parameter to use for calculating the sine
     */
    private PsiElement<Number> parameter;

    /**
     * Creates a new sine function
     *
     * @param parameter the parameter
     * @since 0.1.0
     */
    private PsiSineFunction(PsiElement<Number> parameter) {
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
        return Math.sin(parameter.execute().doubleValue());
    }

    /**
     * A factory for creating sine functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiFactory<PsiSineFunction> {

        /**
         * The pattern for matching sine function expressions
         */
        private static final Pattern PATTERN = Pattern.compile("sin\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiSineFunction parse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<Number> element = (PsiElement<Number>) PsiElementFactory.parseText(expression, Number.class);

            if (element == null)
                throw new ParseException("Function was unable to find an expression named " + expression);

            return new PsiSineFunction(element);
        }
    }
}
