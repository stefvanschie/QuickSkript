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
 * Calculates the cosine of the given number
 *
 * @since 0.1.0
 */
public class PsiCosineFunction extends PsiElement<Double> {

    /**
     * The parameter for calculating the cosine
     */
    private PsiElement<?> parameter;

    /**
     * Creates a cosine function
     *
     * @param parameter the parameter 0.1.0
     */
    private PsiCosineFunction(PsiElement<?> parameter) {
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
        return Math.cos(parameter.execute(context, Number.class).doubleValue());
    }

    /**
     * A factory for creating cosine functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiCosineFunction> {

        /**
         * The pattern for matching cosine expressions
         */
        private final Pattern PATTERN = Pattern.compile("cos\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiCosineFunction tryParse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<?> element = SkriptLoader.get().forceParseElement(expression);

            return new PsiCosineFunction(element);
        }
    }
}
