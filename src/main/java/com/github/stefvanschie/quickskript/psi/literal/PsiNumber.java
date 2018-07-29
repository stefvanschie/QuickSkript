package com.github.stefvanschie.quickskript.psi.literal;

import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A psi element which holds numbers
 *
 * @since 0.1.0
 */
public class PsiNumber implements PsiElement<Double> {

    /**
     * The number this element is holding
     */
    private final double number;

    /**
     * Creates a new psi number
     *
     * @param number the number this psi is wrapping
     */
    private PsiNumber(double number) {
        this.number = number;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public Double execute() {
        return number;
    }

    /**
     * The factory for creating {@link PsiNumber}s.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiFactory<PsiNumber> {

        /**
         * The pattern for matching number literals
         */
        private static final Pattern PATTERN = Pattern.compile("-?\\d+(?:.\\d+)?");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiNumber parse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            return new PsiNumber(Double.parseDouble(text));
        }
    }
}
