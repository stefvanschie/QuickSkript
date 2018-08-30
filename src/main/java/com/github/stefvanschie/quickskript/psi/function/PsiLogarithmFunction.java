package com.github.stefvanschie.quickskript.psi.function;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.psi.PsiFactory;
import com.github.stefvanschie.quickskript.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.psi.exception.ParseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Calculates the logarithm from the given value for the given base
 *
 * @since 0.1.0
 */
public class PsiLogarithmFunction extends PsiElement<Double> {

    /**
     * The value to calculate the logarithm of
     */
    private PsiElement<?> value;

    /**
     * The base for the logarithm
     */
    @Nullable
    private PsiElement<?> base;

    /**
     * Creates a new logarithm function
     *
     * @param value the value
     * @param base the base
     * @since 0.1.0
     */
    private PsiLogarithmFunction(@NotNull PsiElement<?> value, @Nullable PsiElement<?> base) {
        this.value = value;
        this.base = base;

        if (this.value.isPreComputed() && (this.base == null || this.base.isPreComputed())) {
            preComputed = executeImpl(null);
            this.value = this.base = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected Double executeImpl(@Nullable Context context) {
        Object valueResult = value.execute(context);

        if (!(valueResult instanceof Number))
            throw new ExecutionException("Result of expression should be a number, but it wasn't");

        Object baseResult = base == null ? null : base.execute(context);

        if (baseResult != null && !(baseResult instanceof Number))
            throw new ExecutionException("Result of expression should be a number, but it wasn't");

        return Math.log10(((Number) valueResult).doubleValue()) /
            Math.log10(baseResult == null ? 10 : ((Number) baseResult).doubleValue());
    }

    /**
     * A factory for creating logarithm functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiFactory<PsiLogarithmFunction> {

        /**
         * The pattern for matching logarithm expressions
         */
        private static final Pattern PATTERN = Pattern.compile("log\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiLogarithmFunction tryParse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String[] values = matcher.group(1).replace(" ", "").split(",");

            if (values.length < 1 || values.length > 2)
                return null;

            PsiElement<?> value = PsiElementFactory.tryParseText(values[0]);

            if (value == null)
                throw new ParseException("Function was unable to find an expression named " + values[0]);

            PsiElement<?> base = null;

            if (values.length == 2) {
                base = PsiElementFactory.tryParseText(values[1]);

                if (base == null)
                    throw new ParseException("Function was unable to find an expression named " + values[1]);
            }

            return new PsiLogarithmFunction(value, base);
        }
    }
}
