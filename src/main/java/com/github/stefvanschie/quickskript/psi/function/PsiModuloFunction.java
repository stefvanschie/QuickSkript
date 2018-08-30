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
 * Calculates the modulo between two numbers
 *
 * @since 0.1.0
 */
public class PsiModuloFunction extends PsiElement<Double> {

    /**
     * Two numeric values for the calculation
     */
    private PsiElement<?> a, b;

    /**
     * Creates a new modulo function
     *
     * @param a the left hand operator
     * @param b the right hand operator
     */
    private PsiModuloFunction(PsiElement<?> a, PsiElement<?> b) {
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
        Object aResult = a.execute(context);

        if (!(aResult instanceof Number))
            throw new ExecutionException("Result of expression should be a number, but it wasn't");

        Object bResult = b.execute(context);

        if (!(bResult instanceof Number))
            throw new ExecutionException("Result of expression should be a number, but it wasn't");

        return ((Number) aResult).doubleValue() % ((Number) bResult).doubleValue();
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
        public PsiModuloFunction tryParse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String[] values = matcher.group(1).replace(" ", "").split(",");

            if (values.length != 2)
                return null;

            PsiElement<?> a = PsiElementFactory.tryParseText(values[0]);

            if (a == null)
                throw new ParseException("Function was unable to find an expression named " + values[0]);

            PsiElement<?> b = PsiElementFactory.tryParseText(values[1]);

            if (b == null)
                throw new ParseException("Function was unable to find an expression named " + values[1]);

            return new PsiModuloFunction(a, b);
        }
    }
}
