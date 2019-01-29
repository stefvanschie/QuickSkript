package com.github.stefvanschie.quickskript.core.psi.function;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Calculates the atan2 of the given parameters
 *
 * @since 0.1.0
 */
public class PsiAtan2Function extends PsiElement<Double> {

    /**
     * The x and y parameters used to calculate the atan2
     */
    private PsiElement<?> x, y;

    /**
     * Creates a new atan2 function
     *
     * @param x the x parameter
     * @param y the y parameter
     * @param lineNumber the line number
     * @since 0.1.0
     */
    protected PsiAtan2Function(PsiElement<?> x, PsiElement<?> y, int lineNumber) {
        super(lineNumber);

        this.x = x;
        this.y = y;

        if (this.x.isPreComputed() && this.y.isPreComputed()) {
            preComputed = executeImpl(null);
            this.x = this.y = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected Double executeImpl(@Nullable Context context) {
        return Math.atan2(x.execute(context, Number.class).doubleValue(), y.execute(context, Number.class).doubleValue());
    }

    /**
     * A factory for creating atan2 functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiAtan2Function> {

        /**
         * The pattern for matching atan2 function expressions
         */
        private final Pattern pattern = Pattern.compile("atan2\\(([\\s\\S]+),[ ]*([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiAtan2Function tryParse(@NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches()) {
                return null;
            }

            String xExpression = matcher.group(1);
            PsiElement<?> xElement = SkriptLoader.get().forceParseElement(xExpression, lineNumber);

            String yExpression = matcher.group(2);
            PsiElement<?> yElement = SkriptLoader.get().forceParseElement(yExpression, lineNumber);

            return create(xElement, yElement, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param xElement the x element
         * @param yElement the y element
         * @param lineNumber the line number
         * @return the function
         * @since 0.1.0
         */
        @NotNull
        protected PsiAtan2Function create(PsiElement<?> xElement, PsiElement<?> yElement, int lineNumber) {
            return new PsiAtan2Function(xElement, yElement, lineNumber);
        }
    }
}
