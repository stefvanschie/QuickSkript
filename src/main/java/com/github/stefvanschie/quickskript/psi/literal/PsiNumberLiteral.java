package com.github.stefvanschie.quickskript.psi.literal;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiConverter;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A psi element which holds numbers. This is always pre computed.
 *
 * @since 0.1.0
 */
public class PsiNumberLiteral extends PsiElement<Double> {

    /**
     * Creates a new psi number
     *
     * @param number the number this psi is wrapping
     */
    private PsiNumberLiteral(double number) {
        preComputed = number;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public Double executeImpl(@Nullable Context context) {
        throw new AssertionError("Since this preComputed variable is always set, this method should never get called");
    }

    /**
     * The factory for creating {@link PsiNumberLiteral}s.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiFactory<PsiNumberLiteral> {

        /**
         * The pattern for matching number literals
         */
        private static final Pattern PATTERN = Pattern.compile("-?\\d+(?:.\\d+)?");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiNumberLiteral parse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            return new PsiNumberLiteral(Double.parseDouble(text));
        }
    }

    /**
     * A converter to convert other types to this one
     *
     * @since 0.1.0
     */
    public static class Converter implements PsiConverter<PsiNumberLiteral> {

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiNumberLiteral convert(@NotNull Object object) {
            return new PsiNumberLiteral(Double.parseDouble(object.toString()));
        }
    }
}
