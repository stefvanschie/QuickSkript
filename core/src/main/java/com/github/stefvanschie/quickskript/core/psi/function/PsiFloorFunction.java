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
 * Rounds the given number down to the nearest whole integer
 *
 * @since 0.1.0
 */
public class PsiFloorFunction extends PsiElement<Double> {

    /**
     * The parameter for flooring the number
     */
    private PsiElement<?> parameter;

    /**
     * Creates a new floor function
     *
     * @param parameter the parameter
     * @param lineNumber the line number
     * @since 0.1.0
     */
    private PsiFloorFunction(@NotNull PsiElement<?> parameter, int lineNumber) {
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
        return Math.floor(parameter.execute(context, Number.class).doubleValue());
    }

    /**
     * A factory for creating floor functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching floor expressions
         */
        @NotNull
        private final Pattern pattern = Pattern.compile("floor\\((?<parameter>[\\s\\S]+)\\)");

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
        public PsiFloorFunction tryParse(@NotNull SkriptLoader skriptLoader, @NotNull String text, int lineNumber) {
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
        protected PsiFloorFunction create(@NotNull PsiElement<?> element, int lineNumber) {
            return new PsiFloorFunction(element, lineNumber);
        }
    }
}
