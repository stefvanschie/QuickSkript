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
 * Calculates the tangent of a given number
 *
 * @since 0.1.0
 */
public class PsiTangentFunction extends PsiElement<Double> {

    /**
     * The parameter for calculating the tangent
     */
    private PsiElement<?> parameter;

    /**
     * Creates a new tangent function
     *
     * @param parameter the parameter
     * @since 0.1.0
     */
    private PsiTangentFunction(PsiElement<?> parameter) {
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
        return Math.tan(parameter.execute(context, Number.class).doubleValue());
    }

    /**
     * A factory for creating tangent functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiTangentFunction> {

        /**
         * The pattern for matching tangent function expressions
         */
        private final Pattern pattern = Pattern.compile("tan\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiTangentFunction tryParse(@NotNull String text) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<?> element = SkriptLoader.get().forceParseElement(expression);

            return new PsiTangentFunction(element);
        }
    }
}
