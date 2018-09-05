package com.github.stefvanschie.quickskript.psi.function;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.skript.SkriptLoader;
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
     * @since 0.1.0
     */
    private PsiFloorFunction(PsiElement<?> parameter) {
        this.parameter = parameter;

        if (this.parameter.isPreComputed()) {
            preComputed = executeImpl(null);
            this.parameter = null;
        }
    }

    /**
     * {@inheritDoc}
     */
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
    public static class Factory implements PsiElementFactory<PsiFloorFunction> {

        /**
         * The pattern for matching floor expressions
         */
        private final Pattern pattern = Pattern.compile("floor\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiFloorFunction tryParse(@NotNull String text) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<?> element = SkriptLoader.get().forceParseElement(expression);

            return new PsiFloorFunction(element);
        }
    }
}
