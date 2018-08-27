package com.github.stefvanschie.quickskript.psi.function;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.psi.PsiFactory;
import com.github.stefvanschie.quickskript.psi.exception.ParseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Calculates the modulo between two numbers
 *
 * @since 0.1.0
 */
public class PsiModuloFunction extends PsiElement<Double> {

    /**
     * Two numeric values for the calculation
     */
    private PsiElement<Number> a, b;

    /**
     * Creates a new modulo function
     *
     * @param a the left hand operator
     * @param b the right hand operator
     */
    private PsiModuloFunction(PsiElement<Number> a, PsiElement<Number> b) {
        this.a = a;
        this.b = b;

        if (this.a.isPreComputed() && this.b.isPreComputed()) {
            preComputed = executeImpl(null);
            this.a = this.b = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected Double executeImpl(@Nullable Context context) {
        return a.execute(context).doubleValue() % b.execute(context).doubleValue();
    }

    /**
     * A factory for creating modulo functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiFactory<PsiModuloFunction> {

        /**
         * The pattern for matching modulo expressions
         */
        private static final Pattern PATTERN = Pattern.compile("mod\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiModuloFunction parse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String[] values = matcher.group(1).replace(" ", "").split(",");

            if (values.length != 2)
                return null;

            PsiElement<Number> a = (PsiElement<Number>) PsiElementFactory.parseText(values[0], Number.class);

            if (a == null)
                throw new ParseException("Function was unable to find an expression named " + values[0]);

            PsiElement<Number> b = (PsiElement<Number>) PsiElementFactory.parseText(values[1], Number.class);

            if (b == null)
                throw new ParseException("Function was unable to find an expression named " + values[1]);

            return new PsiModuloFunction(a, b);
        }
    }
}
