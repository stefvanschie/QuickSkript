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
     * @param lineNumber the line number
     * @since 0.1.0
     */
    private PsiCalculateExperienceFunction(@NotNull PsiElement<?> parameter, int lineNumber) {
        super(lineNumber);

        this.parameter = parameter;

        if (this.parameter.isPreComputed()) {
            preComputed = executeImpl(null);
            this.parameter = null;
        }
    }

    @NotNull
    @Override
    protected Long executeImpl(@Nullable Context context) {
        long level = parameter.execute(context, Number.class).longValue();
        if (level <= 0) {
            return 0L;
        }

        if (level <= 15) {
            return level * level + 6 * level;
        }
        
        if (level <= 30) {
            return (long) (2.5 * level * level - 40.5 * level - 360);
        }
        
        return (long) (4.5 * level * level - 162.5 * level - 2220);
    }

    /**
     * A factory for creating a calculate experience function
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching calculate experience expressions
         */
        @NotNull
        private final Pattern pattern = Pattern.compile("calcExperience\\((?<parameter>[\\s\\S]+)\\)");

        /**
         * This gets called upon parsing
         *
         * @param skriptLoader the skript loader
         * @param text the text to parse
         * @param lineNumber the line number
         * @return the function, or null to indicate failure
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiCalculateExperienceFunction tryParse(@NotNull SkriptLoader skriptLoader, @NotNull String text,
            int lineNumber) {
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
        protected PsiCalculateExperienceFunction create(@NotNull PsiElement<?> element, int lineNumber) {
            return new PsiCalculateExperienceFunction(element, lineNumber);
        }
    }
}
