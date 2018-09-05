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
        double result = Math.log10(value.execute(context, Number.class).doubleValue());

        if (base != null)
            result /= base.execute(context, Number.class).doubleValue();

        return result;
    }

    /**
     * A factory for creating logarithm functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiLogarithmFunction> {

        /**
         * The pattern for matching logarithm expressions
         */
        private final Pattern pattern = Pattern.compile("log\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiLogarithmFunction tryParse(@NotNull String text) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches())
                return null;

            String[] values = matcher.group(1).replace(" ", "").split(",");

            if (values.length < 1 || values.length > 2)
                return null;

            PsiElement<?> value = SkriptLoader.get().forceParseElement(values[0]);

            PsiElement<?> base = null;

            if (values.length == 2) {
                base = SkriptLoader.get().forceParseElement(values[1]);
            }

            return new PsiLogarithmFunction(value, base);
        }
    }
}
