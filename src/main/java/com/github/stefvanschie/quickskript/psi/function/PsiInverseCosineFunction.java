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
 * Calculates the inverse cosine of the given number
 *
 * @since 0.1.0
 */
public class PsiInverseCosineFunction extends PsiElement<Double> {

    /**
     * The parameter used to calculate the inverse cosine
     */
    private PsiElement<?> parameter;

    /**
     * Creates the inverse cosine
     *
     * @param parameter the parameter for calculating the inverse cosine
     */
    private PsiInverseCosineFunction(PsiElement<?> parameter, int lineNumber) {
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
        return Math.acos(parameter.execute(context, Number.class).doubleValue());
    }

    /**
     * A factory for creating inverse cosine functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiInverseCosineFunction> {

        /**
         * The pattern for matching inverse cosine function expressions
         */
        private final Pattern pattern = Pattern.compile("acos\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiInverseCosineFunction tryParse(@NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<?> element = SkriptLoader.get().forceParseElement(expression, lineNumber);

            return new PsiInverseCosineFunction(element, lineNumber);
        }
    }
}
