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
 * Calculates the amount of exp needed to achieve the given level
 *
 * @since 0.1.0
 */
public class PsiCalculateExperienceFunction extends PsiElement<Long> {

    /**
     * The parameter for calculating the amount of exp
     */
    private PsiElement<?> parameter;

    /**
     * Creates a calculate experience function
     *
     * @param parameter the parameter
     * @since 0.1.0
     */
    private PsiCalculateExperienceFunction(PsiElement<?> parameter) {
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
        Object result = parameter.execute(context);

        if (!(result instanceof Number))
            throw new ExecutionException("Result of expression should be a number, but it wasn't");

        long level = ((Number) result).longValue();
        long exp = 0;

        if (level > 0) {
            if (level <= 15)
                exp = level * level + 6 * level;
            else if (level <= 30)
                exp = (long) (2.5 * level * level - 40.5 * level - 360);
            else
                exp = (long) (4.5 * level * level - 162.5 * level - 2220);
        }

        return exp;
    }

    /**
     * A factory for creating a calculate experience function
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiFactory<PsiCalculateExperienceFunction> {

        /**
         * The pattern for matching calculate experience expressions
         */
        private static final Pattern PATTERN = Pattern.compile("calcExperience\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiCalculateExperienceFunction parse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<?> element = PsiElementFactory.parseText(expression);

            if (element == null)
                throw new ParseException("Function was unable to find an expression named " + expression);

            return new PsiCalculateExperienceFunction(element);
        }
    }
}
