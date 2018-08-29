package com.github.stefvanschie.quickskript.psi.function;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.psi.PsiFactory;
import com.github.stefvanschie.quickskript.psi.exception.ExecutionException;
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
public class PsiAbsoluteValueFunction extends PsiElement<Double> {

    /**
     * The parameter given for this element
     */
    private PsiElement<?> parameter;

    /**
     * Creates the absolute value function
     *
     * @param parameter the parameter for calculating this value
     */
    private PsiAbsoluteValueFunction(PsiElement<?> parameter) {
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

        return Math.abs(((Number) result).doubleValue());
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
            PsiElement<?> element = PsiElementFactory.parseText(expression);

            if (element == null)
                throw new ParseException("Function was unable to find an expression named " + expression);

            return new PsiAbsoluteValueFunction(element);
        }
    }
}
