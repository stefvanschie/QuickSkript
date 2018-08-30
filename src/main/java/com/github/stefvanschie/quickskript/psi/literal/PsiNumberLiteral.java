package com.github.stefvanschie.quickskript.psi.literal;

import com.github.stefvanschie.quickskript.psi.PsiConverter;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A psi element which holds numbers. This is always pre computed.
 *
 * @since 0.1.0
 */
public class PsiNumberLiteral extends PsiPrecomputedHolder<Double> {

    /**
     * Creates a new psi number
     *
     * @param number the number this psi is wrapping
     */
    private PsiNumberLiteral(double number) {
        super(number);
    }

    /**
     * The factory for creating {@link PsiNumberLiteral}s.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiNumberLiteral> {

        /**
         * The pattern for matching number literals
         */
        private final Pattern PATTERN = Pattern.compile("-?\\d+(?:.\\d+)?");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiNumberLiteral tryParse(@NotNull String text) {
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
