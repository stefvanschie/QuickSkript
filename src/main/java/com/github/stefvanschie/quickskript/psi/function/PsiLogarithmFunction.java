package com.github.stefvanschie.quickskript.psi.function;

import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.psi.PsiFactory;
import com.github.stefvanschie.quickskript.psi.exception.ParseException;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Calculates the logarithm from the given value for the given base
 *
 * @since 0.1.0
 */
public class PsiLogarithmFunction implements PsiElement<Double> {

    /**
     * The value to calculate the logarithm of
     */
    @NotNull
    private PsiElement<Number> value;

    /**
     * The base for the logarithm
     */
    @Nullable
    private PsiElement<Number> base;

    /**
     * Creates a new logarithm function
     *
     * @param value the value
     * @param base the base
     * @since 0.1.0
     */
    private PsiLogarithmFunction(@NotNull PsiElement<Number> value, @Nullable PsiElement<Number> base) {
        this.value = value;
        this.base = base;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double execute() {
        return Math.log10(value.execute().doubleValue()) / Math.log10(base == null ? 10 : base.execute().doubleValue());
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
        public PsiLogarithmFunction parse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String[] values = matcher.group(1).replace(" ", "").split(",");

            if (values.length < 1 || values.length > 2)
                return null;

            PsiElement<Number> value = (PsiElement<Number>) PsiElementFactory.parseText(values[0], Number.class);

            if (value == null)
                throw new ParseException("Function was unable to find an expression named " + values[0]);

            PsiElement<Number> base = null;

            if (values.length == 2) {
                base = (PsiElement<Number>) PsiElementFactory.parseText(values[1], Number.class);

                if (base == null)
                    throw new ParseException("Function was unable to find an expression named " + values[1]);
            }

            return new PsiLogarithmFunction(value, base);
        }
    }

    static {
        PsiElementFactory.getClassTypes().put(PsiLogarithmFunction.class, Double.class);
    }
}
