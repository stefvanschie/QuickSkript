package com.github.stefvanschie.quickskript.core.psi.function;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.Contract;
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
    private PsiAtan2Function(@NotNull PsiElement<?> x, @NotNull PsiElement<?> y, int lineNumber) {
        super(lineNumber);

        this.x = x;
        this.y = y;

        if (this.x.isPreComputed() && this.y.isPreComputed()) {
            preComputed = executeImpl(null);
            this.x = this.y = null;
        }
    }

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
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching atan2 function expressions
         */
        @NotNull
        private final Pattern pattern = Pattern.compile("atan2\\((?<x>[\\s\\S]+),[ ]*(?<y>[\\s\\S]+)\\)");

        /**
         * This gets called upon parsing
         *
         * @param text the text to parse
         * @param lineNumber the line number
         * @return the function, or null to indicate failure
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiAtan2Function tryParse(@NotNull SkriptLoader skriptLoader, @NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches()) {
                return null;
            }

            String xExpression = matcher.group("x");
            PsiElement<?> xElement = skriptLoader.forceParseElement(xExpression, lineNumber);

            String yExpression = matcher.group("y");
            PsiElement<?> yElement = skriptLoader.forceParseElement(yExpression, lineNumber);

            return create(xElement, yElement, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the
         * {@link #tryParse(SkriptLoader, String, int)} method.
         *
         * @param xElement the x element
         * @param yElement the y element
         * @param lineNumber the line number
         * @return the function
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        protected PsiAtan2Function create(@NotNull PsiElement<?> xElement, @NotNull PsiElement<?> yElement,
                                          int lineNumber) {
            return new PsiAtan2Function(xElement, yElement, lineNumber);
        }
    }
}
