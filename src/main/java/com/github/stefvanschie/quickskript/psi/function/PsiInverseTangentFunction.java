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
 * Calculates the inverse tangent of a given number
 *
 * @since 0.1.0
 */
public class PsiInverseTangentFunction extends PsiElement<Double> {

    /**
     * The parameter for calculating the inverse tangent
     */
    private PsiElement<?> parameter;

    /**
     * Creates a new inverse tangent function
     *
     * @param parameter the parameter
     * @since 0.1.0
     */
    private PsiInverseTangentFunction(PsiElement<?> parameter) {
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
        return Math.atan(parameter.execute(context, Number.class).doubleValue());
    }

    /**
     * A factory for creating inverse tangent functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiInverseTangentFunction> {

        /**
         * The pattern for matching inverse tangent function expressions
         */
        private final Pattern PATTERN = Pattern.compile("atan\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiInverseTangentFunction tryParse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<?> element = SkriptLoader.get().forceParseElement(expression);

            return new PsiInverseTangentFunction(element);
        }
    }
}
