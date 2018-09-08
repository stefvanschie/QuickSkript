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
 * Calculates the natural logarithm of the given number
 *
 * @since 0.1.0
 */
public class PsiNaturalLogarithmFunction extends PsiElement<Double> {

    /**
     * The parameter for calculating the given number
     */
    private PsiElement<?> parameter;

    /**
     * Creates a new natural logarithm function
     *
     * @param parameter the parameter
     * @since 0.1.0
     */
    private PsiNaturalLogarithmFunction(PsiElement<?> parameter, int lineNumber) {
        super(lineNumber);

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
        return Math.log(parameter.execute(context, Number.class).doubleValue());
    }

    /**
     * A factory for creating natural logarithm functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiNaturalLogarithmFunction> {

        /**
         * The pattern for matching cosine expressions
         */
        private final Pattern pattern = Pattern.compile("ln\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiNaturalLogarithmFunction tryParse(@NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<?> element = SkriptLoader.get().forceParseElement(expression, lineNumber);

            return new PsiNaturalLogarithmFunction(element, lineNumber);
        }
    }
}
