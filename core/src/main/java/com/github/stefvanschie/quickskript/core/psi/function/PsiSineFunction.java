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
 * Calculates the sine of the given number
 *
 * @since 0.1.0
 */
public class PsiSineFunction extends PsiElement<Double> {

    /**
     * The parameter to use for calculating the sine
     */
    private PsiElement<?> parameter;

    /**
     * Creates a new sine function
     *
     * @param parameter the parameter
     * @param lineNumber the line number
     * @since 0.1.0
     */
    private PsiSineFunction(@NotNull PsiElement<?> parameter, int lineNumber) {
        super(lineNumber);

        this.parameter = parameter;

        if (this.parameter.isPreComputed()) {
            preComputed = executeImpl(null);
            this.parameter = null;
        }
    }

    @NotNull
    @Override
    protected Double executeImpl(@Nullable Context context) {
        return Math.sin(parameter.execute(context, Number.class).doubleValue());
    }

    /**
     * A factory for creating sine functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching sine function expressions
         */
        @NotNull
        private final Pattern pattern = Pattern.compile("sin\\((?<parameter>[\\s\\S]+)\\)");

        /**
         * This gets called upon parsing
         *
         * @param skriptLoader the skript loader to parse with
         * @param text the text to parse
         * @param lineNumber the line number
         * @return the function, or null to indicate failure
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiSineFunction tryParse(@NotNull SkriptLoader skriptLoader, @NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches()) {
                return null;
            }

            String expression = matcher.group("parameter");
            PsiElement<?> element = skriptLoader.forceParseElement(expression, lineNumber);

            return create(element, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the
         * {@link #tryParse(SkriptLoader, String, int)} method.
         *
         * @param element the element to compute
         * @param lineNumber the line number
         * @return the function
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        protected PsiSineFunction create(@NotNull PsiElement<?> element, int lineNumber) {
            return new PsiSineFunction(element, lineNumber);
        }
    }
}
