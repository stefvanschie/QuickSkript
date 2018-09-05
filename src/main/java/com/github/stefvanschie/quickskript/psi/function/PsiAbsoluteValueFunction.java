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
 * Calculates the absolute value of a given number
 *
 * @since 0.1.0
 */
public class PsiAbsoluteValueFunction extends PsiElement<Double> {

    /**
     * The parameter given for this element
     */
    private PsiElement<?> parameter;

    /**
     * Creates the absolute value function
     *
     * @param parameter the parameter for calculating this value
     */
    private PsiAbsoluteValueFunction(PsiElement<?> parameter) {
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
        return Math.abs(parameter.execute(context, Number.class).doubleValue());
    }

    /**
     * The factory for creating {@link PsiAbsoluteValueFunction}s.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiAbsoluteValueFunction> {

        /**
         * The pattern for matching absolute value functions
         */
        private final Pattern pattern = Pattern.compile("abs\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiAbsoluteValueFunction tryParse(@NotNull String text) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<?> element = SkriptLoader.get().forceParseElement(expression);

            return new PsiAbsoluteValueFunction(element);
        }
    }
}
