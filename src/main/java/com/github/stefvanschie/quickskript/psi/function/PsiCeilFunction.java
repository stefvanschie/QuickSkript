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
 * Rounds the provided number up to the nearest integer
 *
 * @since 0.1.0
 */
public class PsiCeilFunction extends PsiElement<Double> {

    /**
     * The parameter to round up
     */
    private PsiElement<?> parameter;

    /**
     * Creates a ceil function
     *
     * @param parameter the parameter
     * @since 0.1.0
     */
    private PsiCeilFunction(PsiElement<?> parameter) {
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
        return Math.ceil(parameter.execute(context, Number.class).doubleValue());
    }

    /**
     * The factory for creating ceil functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiCeilFunction> {

        /**
         * The pattern for matching ceil expressions
         */
        private final Pattern PATTERN = Pattern.compile("ceil(?:ing)?\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiCeilFunction tryParse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<?> element = SkriptLoader.get().forceParseElement(expression);

            return new PsiCeilFunction(element);
        }
    }
}
