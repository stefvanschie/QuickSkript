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
 * Calculates the square root of the given number
 *
 * @since 0.1.0
 */
public class PsiSquareRootFunction extends PsiElement<Double> {

    /**
     * The parameter to use for calculating the square root
     */
    private PsiElement<?> parameter;

    /**
     * Creates a new square root function
     *
     * @param parameter the parameter
     * @since 0.1.0
     */
    private PsiSquareRootFunction(PsiElement<?> parameter, int lineNumber) {
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
        return Math.sqrt(parameter.execute(context, Number.class).doubleValue());
    }

    /**
     * A factory for creating square root functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiSquareRootFunction> {

        /**
         * The pattern for matching square root function expressions
         */
        private final Pattern pattern = Pattern.compile("sqrt\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiSquareRootFunction tryParse(@NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<?> element = SkriptLoader.get().forceParseElement(expression, lineNumber);

            return new PsiSquareRootFunction(element, lineNumber);
        }
    }
}
