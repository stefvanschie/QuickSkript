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
 * Calculates the inverse sine of the given number
 *
 * @since 0.1.0
 */
public class PsiInverseSineFunction extends PsiElement<Double> {

    /**
     * The parameter to use for calculating the inverse sine
     */
    private PsiElement<?> parameter;

    /**
     * Creates a new inverse sine function
     *
     * @param parameter the parameter
     * @since 0.1.0
     */
    private PsiInverseSineFunction(PsiElement<?> parameter) {
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

        return Math.asin(((Number) result).doubleValue());
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
            PsiElement<?> element = PsiElementFactory.parseText(expression);

            if (element == null)
                throw new ParseException("Function was unable to find an expression named " + expression);

            return new PsiInverseSineFunction(element);
        }
    }
}
