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
     * @param lineNumber the line number
     * @since 0.1.0
     */
    private PsiLogarithmFunction(@NotNull PsiElement<?> value, @Nullable PsiElement<?> base, int lineNumber) {
        super(lineNumber);

        this.value = value;
        this.base = base;

        if (this.value.isPreComputed() && (this.base == null || this.base.isPreComputed())) {
            preComputed = executeImpl(null);
            this.value = this.base = null;
        }
    }

    @NotNull
    @Override
    protected Double executeImpl(@Nullable Context context) {
        double result = Math.log10(value.execute(context, Number.class).doubleValue());

        if (base != null) {
            result /= base.execute(context, Number.class).doubleValue();
        }

        return result;
    }

    /**
     * A factory for creating logarithm functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching logarithm expressions
         */
        @NotNull
        private final Pattern pattern = Pattern.compile("log\\((?<parameters>[\\s\\S]+)\\)");

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
        public PsiLogarithmFunction tryParse(@NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches()) {
                return null;
            }

            String[] values = matcher.group("parameters").replace(" ", "").split(",");

            if (values.length < 1 || values.length > 2) {
                return null;
            }

            PsiElement<?> value = SkriptLoader.get().forceParseElement(values[0], lineNumber);

            PsiElement<?> base = null;

            if (values.length == 2) {
                base = SkriptLoader.get().forceParseElement(values[1], lineNumber);
            }

            return create(value, base, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param value the value of the logarithm
         * @param base the base of the logarithm
         * @param lineNumber the line number
         * @return the function
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        protected PsiLogarithmFunction create(@NotNull PsiElement<?> value, @Nullable PsiElement<?> base,
                                              int lineNumber) {
            return new PsiLogarithmFunction(value, base, lineNumber);
        }
    }
}
