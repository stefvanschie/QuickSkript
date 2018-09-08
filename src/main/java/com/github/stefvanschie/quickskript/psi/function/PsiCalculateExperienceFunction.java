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
    private PsiCalculateExperienceFunction(PsiElement<?> parameter, int lineNumber) {
        super(lineNumber);

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
        long level = parameter.execute(context, Number.class).longValue();
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
    public static class Factory implements PsiElementFactory<PsiCalculateExperienceFunction> {

        /**
         * The pattern for matching calculate experience expressions
         */
        private final Pattern pattern = Pattern.compile("calcExperience\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiCalculateExperienceFunction tryParse(@NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<?> element = SkriptLoader.get().forceParseElement(expression, lineNumber);

            return new PsiCalculateExperienceFunction(element, lineNumber);
        }
    }
}
