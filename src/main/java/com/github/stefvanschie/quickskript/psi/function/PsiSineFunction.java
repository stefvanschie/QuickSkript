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
        return Math.sin(parameter.execute(context, Number.class).doubleValue());
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
        private final Pattern PATTERN = Pattern.compile("sin\\(([\\s\\S]+)\\)");

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
            PsiElement<?> element = SkriptLoader.get().forceParseElement(expression);

            return new PsiSineFunction(element);
        }
    }
}
