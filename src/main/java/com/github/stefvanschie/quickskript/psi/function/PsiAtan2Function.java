package com.github.stefvanschie.quickskript.psi.function;

import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.psi.PsiFactory;
import com.github.stefvanschie.quickskript.psi.exception.ParseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Calculates the atan2 of the given parameters
 *
 * @since 0.1.0
 */
public class PsiAtan2Function implements PsiElement<Double> {

    /**
     * The x and y parameters used to calculate the atan2
     */
    private PsiElement<Number> x, y;

    /**
     * Creates a new atan2 function
     *
     * @param x the x parameter
     * @param y the y parameter
     * @since 0.1.0
     */
    private PsiAtan2Function(PsiElement<Number> x, PsiElement<Number> y) {
        this.x = x;
        this.y = y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double execute() {
        return Math.atan2(x.execute().doubleValue(), y.execute().doubleValue());
    }

    /**
     * A factory for creating atan2 functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiFactory<PsiAtan2Function> {

        /**
         * The pattern for matching atan2 function expressions
         */
        private static final Pattern PATTERN = Pattern.compile("atan2\\(([\\s\\S]+),[ ]*([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiAtan2Function parse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String xExpression = matcher.group(1);
            PsiElement<Number> xElement = (PsiElement<Number>) PsiElementFactory.parseText(xExpression, Number.class);

            if (xElement == null)
                throw new ParseException("Function was unable to find an expression named " + xExpression);

            String yExpression = matcher.group(2);
            PsiElement<Number> yElement = (PsiElement<Number>) PsiElementFactory.parseText(yExpression, Number.class);

            if (yElement == null)
                throw new ParseException("Function was unable to find an expression named " + yExpression);

            return new PsiAtan2Function(xElement, yElement);
        }
    }

    static {
        PsiElementFactory.getClassTypes().put(PsiAtan2Function.class, Double.class);
    }
}
