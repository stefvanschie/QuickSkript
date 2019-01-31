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
     * @param lineNumber the line number
     * @since 0.1.0
     */
    protected PsiModuloFunction(PsiElement<?> a, PsiElement<?> b, int lineNumber) {
        super(lineNumber);

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
        return a.execute(context, Number.class).doubleValue() % b.execute(context, Number.class).doubleValue();
    }

    /**
     * A factory for creating modulo functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiModuloFunction> {

        /**
         * The pattern for matching modulo expressions
         */
        private final Pattern pattern = Pattern.compile("mod\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiModuloFunction tryParse(@NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches()) {
                return null;
            }

            String[] values = matcher.group(1).replace(" ", "").split(",");

            if (values.length != 2) {
                return null;
            }

            PsiElement<?> a = SkriptLoader.get().forceParseElement(values[0], lineNumber);
            PsiElement<?> b = SkriptLoader.get().forceParseElement(values[1], lineNumber);

            return create(a, b, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param a the left side of the expression
         * @param b the right side of the expression
         * @param lineNumber the line number
         * @return the function
         * @since 0.1.0
         */
        @NotNull
        protected PsiModuloFunction create(PsiElement<?> a, PsiElement<?> b, int lineNumber) {
            return new PsiModuloFunction(a, b, lineNumber);
        }
    }
}