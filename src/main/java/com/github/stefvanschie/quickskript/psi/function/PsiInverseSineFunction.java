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
        return Math.asin(parameter.execute(context, Number.class).doubleValue());
    }

    /**
     * A factory for creating inverse sine functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiInverseSineFunction> {

        /**
         * The pattern for matching inverse sine function expressions
         */
        private final Pattern PATTERN = Pattern.compile("asin\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiInverseSineFunction tryParse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<?> element = SkriptLoader.get().forceParseElement(expression);

            return new PsiInverseSineFunction(element);
        }
    }
}
