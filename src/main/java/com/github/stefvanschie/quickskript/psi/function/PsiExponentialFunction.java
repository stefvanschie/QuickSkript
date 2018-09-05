package com.github.stefvanschie.quickskript.psi.function;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.skript.SkriptLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Calculates e to the power of the given number
 *
 * @since 0.1.0
 */
public class PsiExponentialFunction extends PsiElement<Double> {

    /**
     * The parameter used to calculate the exponent
     */
    private PsiElement<?> parameter;

    /**
     * Creates a new exponential function
     *
     * @param parameter the parameter
     * @since 0.1.0
     */
    private PsiExponentialFunction(PsiElement<?> parameter) {
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
        return Math.exp(parameter.execute(context, Number.class).doubleValue());
    }

    /**
     * A factory for creating exponential functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiExponentialFunction> {

        /**
         * The pattern for matching cosine expressions
         */
        private final Pattern pattern = Pattern.compile("exp\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiExponentialFunction tryParse(@NotNull String text) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<?> element = SkriptLoader.get().forceParseElement(expression);

            return new PsiExponentialFunction(element);
        }
    }
}
