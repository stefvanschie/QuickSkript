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
 * Rounds the given value to the closest integer
 *
 * @since 0.1.0
 */
public class PsiRoundFunction extends PsiElement<Long> {

    /**
     * The value to round
     */
    private PsiElement<Number> parameter;

    /**
     * Creates a new round function
     *
     * @param parameter the parameter
     * @since 0.1.0
     */
    private PsiRoundFunction(@NotNull PsiElement<Number> parameter) {
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
    protected Long executeImpl(@Nullable Context context) {
        return Math.round(parameter.execute(context).doubleValue());
    }

    /**
     * A factory for creating round functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiFactory<PsiRoundFunction> {

        /**
         * The pattern for matching round expressions
         */
        private static final Pattern PATTERN = Pattern.compile("round\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiRoundFunction parse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<Number> element = (PsiElement<Number>) PsiElementFactory.parseText(expression, Number.class);

            if (element == null)
                throw new ParseException("Function was unable to find an expression named " + expression);

            return new PsiRoundFunction(element);
        }
    }
}
