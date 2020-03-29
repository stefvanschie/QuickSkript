package com.github.stefvanschie.quickskript.core.psi.function;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Calculates the absolute value of a given number
 *
 * @since 0.1.0
 */
public class PsiAbsoluteValueFunction extends PsiElement<Double> {

    /**
     * The parameter given for this element
     */
    private PsiElement<?> parameter;

    /**
     * Creates the absolute value function
     *
     * @param parameter the parameter for calculating this value
     * @param lineNumber the line number
     * @since 0.1.0
     */
    private PsiAbsoluteValueFunction(@NotNull PsiElement<?> parameter, int lineNumber) {
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
        return Math.abs(parameter.execute(context, Number.class).doubleValue());
    }

    /**
     * The factory for creating {@link PsiAbsoluteValueFunction}s.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching absolute value functions
         */
        @NotNull
        private final SkriptPattern pattern = SkriptPattern.parse("abs\\(%number%\\)");

        /**
         * This gets called upon parsing
         *
         * @param number the number
         * @param lineNumber the line number
         * @return the function, or null to indicate failure
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiAbsoluteValueFunction tryParse(@NotNull PsiElement<?> number, int lineNumber) {
            return create(number, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(PsiElement, int)}
         * method.
         *
         * @param element the element to compute
         * @param lineNumber the lien number
         * @return the function
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        protected PsiAbsoluteValueFunction create(@NotNull PsiElement<?> element, int lineNumber) {
            return new PsiAbsoluteValueFunction(element, lineNumber);
        }
    }
}
