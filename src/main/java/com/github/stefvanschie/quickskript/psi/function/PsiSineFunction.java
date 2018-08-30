package com.github.stefvanschie.quickskript.psi.function;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementUtil;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.psi.exception.ExecutionException;
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
    private PsiElement<?> parameter;

    /**
     * Creates a new sine function
     *
     * @param parameter the parameter
     * @since 0.1.0
     */
    private PsiSineFunction(PsiElement<?> parameter) {
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

        return Math.sin(((Number) result).doubleValue());
    }

    /**
     * A factory for creating sine functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiSineFunction> {

        /**
         * The pattern for matching sine function expressions
         */
        private static final Pattern PATTERN = Pattern.compile("sin\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiSineFunction tryParse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<?> element = PsiElementUtil.tryParseText(expression);

            if (element == null)
                throw new ParseException("Function was unable to find an expression named " + expression);

            return new PsiSineFunction(element);
        }
    }
}
